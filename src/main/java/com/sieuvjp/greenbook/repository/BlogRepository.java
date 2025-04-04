package com.sieuvjp.greenbook.repository;

import com.sieuvjp.greenbook.entity.Blog;
import com.sieuvjp.greenbook.entity.User;
import com.sieuvjp.greenbook.enums.BlogStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    Page<Blog> findByStatus(BlogStatus status, Pageable pageable);

    List<Blog> findByUser(User user);

    Page<Blog> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    List<Blog> findByStatusAndPublishedDateBefore(BlogStatus status, LocalDateTime date);

    List<Blog> findTop5ByStatusOrderByPublishedDateDesc(BlogStatus status);
}