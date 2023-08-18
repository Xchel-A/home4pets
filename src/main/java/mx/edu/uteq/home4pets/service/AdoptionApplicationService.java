package mx.edu.uteq.home4pets.service;

import mx.edu.uteq.home4pets.entity.AdoptionApplication;
import mx.edu.uteq.home4pets.model.request.adoption.AdoptionRegisterDto;
import mx.edu.uteq.home4pets.model.request.adoption.AdoptionUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AdoptionApplicationService {

    Page<AdoptionApplication> findAllAdoptionApplications(Pageable pageable);

    Page<AdoptionApplication> findAdoptionApplicationsByUsername(String username, Pageable pageable);

    Optional<AdoptionApplication> findAdoptionApplicationId(Long id);

    boolean createApplication(AdoptionRegisterDto adoptionRegisterDto);

    boolean changeStateAdoption(AdoptionUpdateDto adoptionUpdateDto);
}
