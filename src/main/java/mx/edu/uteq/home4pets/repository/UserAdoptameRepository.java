package mx.edu.uteq.home4pets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import mx.edu.uteq.home4pets.entity.UserAdoptame;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserAdoptameRepository extends JpaRepository<UserAdoptame, Long> {

    UserAdoptame findUserByUsername(String username);

    // Metodo para encontrar a un usuario por email
    @Query("SELECT u FROM UserAdoptame u WHERE u.username = ?1")
    UserAdoptame findUserByEmail(String email);
}
