package mx.edu.uteq.home4pets.service;

import mx.edu.uteq.home4pets.entity.MovementManagement;
import mx.edu.uteq.home4pets.entity.Pet;
import mx.edu.uteq.home4pets.model.request.pet.PetInsertDto;
import mx.edu.uteq.home4pets.model.request.pet.PetSearchDto;
import mx.edu.uteq.home4pets.model.request.pet.PetTracingRegisterDto;
import mx.edu.uteq.home4pets.model.request.pet.PetUpdateDto;
import mx.edu.uteq.home4pets.repository.PetRepository;
import mx.edu.uteq.home4pets.util.InfoMovement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validator;
import java.util.*;

@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    private final MovementManagementServiceImpl movementManagementService;

    private final InfoMovement infoMovement;

    @Autowired
    private Validator validator;

    private final Logger logger = LoggerFactory.getLogger(PetServiceImpl.class);

    public PetServiceImpl(PetRepository repository, MovementManagementServiceImpl movementManagementService,
                          InfoMovement infoMovement) {
        this.petRepository = repository;
        this.movementManagementService = movementManagementService;
        this.infoMovement = infoMovement;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Pet> findAll(Pageable pageable) {
        return petRepository.findAll(pageable);
    }

    /*@Override
    @Transactional(readOnly = true)
    public Page<Pet> findAllByUser(int id, Pageable pageable) {
        return petRepository.findAll(pageable);
    }*/

    @Override
    @Transactional(readOnly = true)
    public Page<Pet> findPetsToAdopt(String type, String isAccepted, Pageable pageable) {
        return petRepository.findAllByAvailableAdoptionAndTypeAndIsAccepted(true, type, isAccepted, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Pet> findPetsByColorSizeOrPersonality(PetSearchDto petSearchDto, Pageable pageable) {

        String typePet = petSearchDto.getTypePet();
        Long colorId = petSearchDto.getColorId();
        Long sizeId = petSearchDto.getSizeId();
        Long personalityId = petSearchDto.getPersonalityId();

        return petRepository.findPetsByColorSizeOrPersonalityToAdopt(typePet, colorId, sizeId, personalityId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pet> findPetById(Long id) {
        return petRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean deletePetById(Long id) {
        boolean flagDelete = false;
        int petDeleted = petRepository.deletePetById(id);
        System.out.println("petDeleted: " + petDeleted);
        if (petDeleted != 0) {
            flagDelete = true;
        }
        return flagDelete;
    }

    @Override
    public boolean create(PetInsertDto petDto, String imageName) {

        boolean flagInsert = false;

        Pet pet = new Pet();

        BeanUtils.copyProperties(petDto, pet);

        pet.setAvailableAdoption(true);
        pet.setCreatedAt(new Date());
        pet.setImage(imageName);
        pet.setIsAccepted("pendiente");

        try {
            Pet petInserted = petRepository.save(pet);

            if (petInserted.getId() != 0) {
                flagInsert = true;
                MovementManagement movement = new MovementManagement();

                movement.setModuleName(infoMovement.getModuleName());
                movement.setUsername(infoMovement.getUsername());
                movement.setAction(infoMovement.getActionMovement());
                movement.setMovementDate(new Date());
                movement.setNewData(petInserted.toString());

                movementManagementService.createOrUpdate(movement);
            }
        } catch (Exception ex) {
            logger.error("error to insert a pet");
        }

        return flagInsert;
    }

    @Override
    public boolean update(PetUpdateDto petDto) {
        boolean flagUpdate = false;

        Optional<Pet> previousData = petRepository.findById(petDto.getId());

        if (previousData.isPresent()) {
            try {
                Pet petToUpdate = new Pet();

                BeanUtils.copyProperties(previousData.get(), petToUpdate);
                BeanUtils.copyProperties(petDto, petToUpdate);

                Pet petUpdated = petRepository.save(petToUpdate);

                if (petUpdated.getId() != 0) {
                    flagUpdate = true;

                    MovementManagement movement = new MovementManagement();

                    movement.setModuleName(infoMovement.getModuleName());
                    movement.setUsername(infoMovement.getUsername());
                    movement.setAction(infoMovement.getActionMovement());
                    movement.setMovementDate(new Date());
                    movement.setPreviousData(previousData.get().toString());
                    movement.setNewData(petUpdated.toString());

                    movementManagementService.createOrUpdate(movement);
                }
            } catch (Exception ex) {
                logger.error("error to update a pet");
            }
        }

        return flagUpdate;
    }

    @Override
    public boolean acceptOrRejectPet(PetTracingRegisterDto petDto) {
        boolean flag = false;
        Optional<Pet> previousData = petRepository.findById(petDto.getId());

        if (previousData.isPresent()) {
            try {
                Pet petToUpdate = new Pet();

                BeanUtils.copyProperties(previousData.get(), petToUpdate);
                BeanUtils.copyProperties(petDto, petToUpdate);

                Pet petAcceptedOrRejected = petRepository.save(petToUpdate);

                if (petAcceptedOrRejected.getId() != 0) {
                    flag = true;

                    MovementManagement movement = new MovementManagement();

                    movement.setModuleName(infoMovement.getModuleName());
                    movement.setUsername(infoMovement.getUsername());
                    movement.setAction(infoMovement.getActionMovement());
                    movement.setMovementDate(new Date());
                    movement.setPreviousData(previousData.get().toString());
                    movement.setNewData(petAcceptedOrRejected.toString());

                    movementManagementService.createOrUpdate(movement);
                }
            } catch (Exception ex) {
                logger.error("error to accepted a pet");
            }
        }
        return flag;
    }

    @Override
    public boolean changeAvailableAdoptionPet(Long id, Boolean availableAdoptionPet) {
        boolean flag = false;

        Optional<Pet> petExisted = petRepository.findById(id);

        if (petExisted.isPresent()) {
            try {
                int isRowAffected = petRepository.changeAvailableAdoptionPet(id, availableAdoptionPet);

                if (isRowAffected == 1) {
                    Optional<Pet> petUpdated = petRepository.findById(id);

                    flag = true;

                    if (petUpdated.isPresent()) {
                        MovementManagement movement = new MovementManagement();

                        movement.setModuleName(infoMovement.getModuleName());
                        movement.setUsername(infoMovement.getUsername());
                        movement.setAction(infoMovement.getActionMovement());
                        movement.setMovementDate(new Date());
                        movement.setPreviousData(petExisted.get().toString());
                        movement.setNewData(petUpdated.get().toString());

                        movementManagementService.createOrUpdate(movement);

                    }
                }
            } catch (Exception ex) {
                logger.error("error to change available adoption pet");
            }
        }

        return flag;
    }

    public Map<String, List<String>> getValidationToInsertPet(PetInsertDto petDto) {
        Set<ConstraintViolation<PetInsertDto>> violations = validator.validate(petDto);

        Map<String, List<String>> errors = new HashMap<>();

        if (!violations.isEmpty()) {

            for (ConstraintViolation<PetInsertDto> error : violations) {
                List<String> messagesToInsert = new ArrayList<>();

                Path path = error.getPropertyPath();
                String key = path.toString();
                String message = error.getMessage();

                if (errors.get(key) != null) {
                    errors.get(key).add(message);
                } else {
                    messagesToInsert.add(message);
                    errors.put(key, messagesToInsert);
                }

            }
        }
        return errors;
    }

    public Map<String, List<String>> getValidationToUpdatePet(PetUpdateDto petDto) {
        Set<ConstraintViolation<PetUpdateDto>> violations = validator.validate(petDto);

        Map<String, List<String>> errors = new HashMap<>();

        if (!violations.isEmpty()) {

            for (ConstraintViolation<PetUpdateDto> error : violations) {

                List<String> messagesToUpdate = new ArrayList<>();

                Path path = error.getPropertyPath();
                String key = path.toString();
                String message = error.getMessage();

                if (errors.get(key) != null) {
                    errors.get(key).add(message);
                } else {
                    messagesToUpdate.add(message);
                    errors.put(key, messagesToUpdate);
                }
            }
        }
        return errors;
    }


    public Map<String, List<String>> getValidationToAcceptOrReject(PetTracingRegisterDto petDto) {
        Set<ConstraintViolation<PetTracingRegisterDto>> violations = validator.validate(petDto);

        Map<String, List<String>> errors = new HashMap<>();

        if (!violations.isEmpty()) {

            for (ConstraintViolation<PetTracingRegisterDto> error : violations) {
                List<String> messagesToAcceptOrReject = new ArrayList<>();

                Path path = error.getPropertyPath();
                String key = path.toString();
                String message = error.getMessage();

                if (errors.get(key) != null) {
                    errors.get(key).add(message);
                } else {
                    messagesToAcceptOrReject.add(message);
                    errors.put(key, messagesToAcceptOrReject);
                }

            }
        }
        return errors;
    }
}
