package mx.edu.uteq.home4pets.service;

import mx.edu.uteq.home4pets.entity.Pet;
import mx.edu.uteq.home4pets.model.request.pet.PetInsertDto;
import mx.edu.uteq.home4pets.model.request.pet.PetSearchDto;
import mx.edu.uteq.home4pets.model.request.pet.PetTracingRegisterDto;
import mx.edu.uteq.home4pets.model.request.pet.PetUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PetService {

    Page<Pet> findAll(Pageable pageable);

    Page<Pet> findPetsToAdopt(String type, String isAccepted, Pageable pageable);

    Page<Pet> findPetsByColorSizeOrPersonality(PetSearchDto petSearchDto, Pageable pageable);

    Optional<Pet> findPetById(Long id);

    boolean deletePetById(Long id);

    boolean create(PetInsertDto pet, String imageName);

    boolean update(PetUpdateDto pet);

    boolean acceptOrRejectPet(PetTracingRegisterDto pet);

    boolean changeAvailableAdoptionPet(Long id, Boolean AvailableAdoptionPet);

}
