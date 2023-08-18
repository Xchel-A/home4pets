package mx.edu.uteq.home4pets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import mx.edu.uteq.home4pets.entity.MovementManagement;
@Repository
public interface MovementManagementRepository extends JpaRepository<MovementManagement, Long> {
}
