package com.sieuvjp.greenbook.service;

import com.sieuvjp.greenbook.entity.Blog;
import com.sieuvjp.greenbook.enums.BlogStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface BlogService {
    List<Blog> findAll();
    Page<Blog> findAllPaginated(Pageable pageable);
    Optional<Blog> findById(Long id);
    Blog save(Blog blog);
    void deleteById(Long id);
    Page<Blog> findByStatus(BlogStatus status, Pageable pageable);
    List<Blog> findByUserId(Long userId);
    Page<Blog> searchBlogs(String keyword, Pageable pageable);
    List<Blog> findRecentPublishedBlogs(int limit);
    void updateBlogStatus(Long blogId, BlogStatus status);
    void uploadBlogImage(Long blogId, MultipartFile imageFile);
}