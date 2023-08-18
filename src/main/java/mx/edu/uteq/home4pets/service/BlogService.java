package mx.edu.uteq.home4pets.service;

import mx.edu.uteq.home4pets.entity.Blog;
import mx.edu.uteq.home4pets.model.request.blog.BlogInsertDto;
import mx.edu.uteq.home4pets.model.request.blog.BlogUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface BlogService {
    Page<Blog> findAllBlog(Pageable pageable);
    Optional<Blog> findBlogById(Long id);
    boolean saveBlog(BlogInsertDto blog, String imageName, String username);
    boolean updateBlog(BlogUpdateDto blog);
    List<Blog> findAllByIsPrincipal(Boolean isPrincipal);

    boolean deleteBlogById(Long id);

}
