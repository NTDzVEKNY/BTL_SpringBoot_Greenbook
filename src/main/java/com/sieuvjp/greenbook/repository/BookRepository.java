package com.sieuvjp.greenbook.repository;

import com.sieuvjp.greenbook.entity.Book;
import com.sieuvjp.greenbook.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findByIsActive(boolean isActive, Pageable pageable);

    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Book> findByCategory(Category category, Pageable pageable);

    List<Book> findByIsActiveAndStockQuantityGreaterThan(boolean isActive, int minStock);

    @Query("SELECT b FROM Book b WHERE b.soldQuantity > 0 ORDER BY b.soldQuantity DESC")
    List<Book> findBestSellingBooks(Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.createdAt >= CURRENT_DATE - 30")
    List<Book> findNewArrivals(Pageable pageable);
}