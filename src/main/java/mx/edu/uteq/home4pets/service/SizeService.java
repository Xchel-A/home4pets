package mx.edu.uteq.home4pets.service;

import mx.edu.uteq.home4pets.entity.Size;
import java.util.List;
import java.util.Optional;

public interface SizeService {
    List<Size> findAllSizes();

    Long countAllSizes();

    Optional<Size> findSizeById(Long id);
}
