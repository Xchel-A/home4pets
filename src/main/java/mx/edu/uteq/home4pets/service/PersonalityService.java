package mx.edu.uteq.home4pets.service;

import mx.edu.uteq.home4pets.entity.Personality;

import java.util.List;
import java.util.Optional;

public interface PersonalityService {
    List<Personality> findAllPersonalities();

    Long countAllPersonalities();

    Optional<Personality> findPersonalityById(Long id);
}
