package mx.edu.uteq.home4pets.service;

import mx.edu.uteq.home4pets.entity.Color;

import java.util.List;
import java.util.Optional;

public interface ColorService {
    List<Color> findAllColors();

    Long countAllColors();

    Optional<Color> findColorById(Long id);
}

