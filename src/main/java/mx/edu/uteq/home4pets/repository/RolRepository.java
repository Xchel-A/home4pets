package mx.edu.uteq.home4pets.repository;

import java.util.Optional;

import mx.edu.uteq.home4pets.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RolRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRolByName(String name);
}
