package mx.edu.uteq.home4pets.repository;

import java.util.List;

import mx.edu.uteq.home4pets.entity.Blog;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface BlogRepository extends PagingAndSortingRepository<Blog,Long> {
    List<Blog> findAllByIsPrincipal(Boolean isPrincipal);
    Page<Blog> findAllByOrderByCreatedAtDesc(Pageable peagable);

    @Transactional
    @Modifying
    @Query("DELETE FROM Blog b WHERE b.id = ?1")
    int deleteBlogById(Long id);
}
