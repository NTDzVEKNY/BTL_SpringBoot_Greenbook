package com.sieuvjp.greenbook.service;

import com.sieuvjp.greenbook.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();
    List<Category> findAllActive();
    Optional<Category> findById(Long id);
    Optional<Category> findByName(String name);
    Category save(Category category);
    void deleteById(Long id);
    boolean existsByName(String name);
    void toggleActiveStatus(Long categoryId);
}