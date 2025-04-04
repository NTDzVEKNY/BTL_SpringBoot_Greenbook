package com.sieuvjp.greenbook.service.impl;

import com.sieuvjp.greenbook.entity.Blog;
import com.sieuvjp.greenbook.entity.User;
import com.sieuvjp.greenbook.enums.BlogStatus;
import com.sieuvjp.greenbook.exception.ResourceNotFoundException;
import com.sieuvjp.greenbook.repository.BlogRepository;
import com.sieuvjp.greenbook.repository.UserRepository;
import com.sieuvjp.greenbook.service.BlogService;
import com.sieuvjp.greenbook.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final FileUploadUtil fileUploadUtil;

    @Override
    public List<Blog> findAll() {
        return blogRepository.findAll();
    }

    @Override
    public Page<Blog> findAllPaginated(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Optional<Blog> findById(Long id) {
        return blogRepository.findById(id);
    }

    @Override
    @Transactional
    public Blog save(Blog blog) {
        if (blog.getStatus() == BlogStatus.PUBLISHED && blog.getPublishedDate() == null) {
            blog.setPublishedDate(LocalDateTime.now());
        }
        return blogRepository.save(blog);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found with id: " + id));

        // Delete image file if exists
        if (blog.getImageUrl() != null && !blog.getImageUrl().isEmpty()) {
            try {
                String fileName = blog.getImageUrl().substring(blog.getImageUrl().lastIndexOf("/") + 1);
                fileUploadUtil.deleteFile("blogs/" + id + "/" + fileName);
            } catch (IOException e) {
                // Log error but continue with deletion
            }
        }

        blogRepository.deleteById(id);
    }

    @Override
    public Page<Blog> findByStatus(BlogStatus status, Pageable pageable) {
        return blogRepository.findByStatus(status, pageable);
    }

    @Override
    public List<Blog> findByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        return blogRepository.findByUser(user);
    }

    @Override
    public Page<Blog> searchBlogs(String keyword, Pageable pageable) {
        return blogRepository.findByTitleContainingIgnoreCase(keyword, pageable);
    }

    @Override
    public List<Blog> findRecentPublishedBlogs(int limit) {
        return blogRepository.findTop5ByStatusOrderByPublishedDateDesc(BlogStatus.PUBLISHED);
    }

    @Override
    @Transactional
    public void updateBlogStatus(Long blogId, BlogStatus status) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found with id: " + blogId));

        blog.setStatus(status);

        if (status == BlogStatus.PUBLISHED && blog.getPublishedDate() == null) {
            blog.setPublishedDate(LocalDateTime.now());
        }

        blogRepository.save(blog);
    }

    @Override
    @Transactional
    public void uploadBlogImage(Long blogId, MultipartFile imageFile) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found with id: " + blogId));

        try {
            // Delete old image if exists
            if (blog.getImageUrl() != null && !blog.getImageUrl().isEmpty()) {
                String oldFileName = blog.getImageUrl().substring(blog.getImageUrl().lastIndexOf("/") + 1);
                fileUploadUtil.deleteFile("blogs/" + blogId + "/" + oldFileName);
            }

            String fileName = fileUploadUtil.saveFile(imageFile, "blogs/" + blogId);
            String imageUrl = "/uploads/blogs/" + blogId + "/" + fileName;

            blog.setImageUrl(imageUrl);
            blogRepository.save(blog);
        } catch (IOException e) {
            throw new RuntimeException("Could not store the image file", e);
        }
    }
}