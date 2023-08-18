package mx.edu.uteq.home4pets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import mx.edu.uteq.home4pets.entity.Color;
@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
}
