package mx.edu.uteq.home4pets.repository;

import mx.edu.uteq.home4pets.entity.AdoptionApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdoptionApplicationRepository extends PagingAndSortingRepository<AdoptionApplication, Long> {

    @Query("SELECT a FROM AdoptionApplication a WHERE a.user.username LIKE ?1")
    Page<AdoptionApplication> findAllByUser(String username, Pageable pageable);

}
