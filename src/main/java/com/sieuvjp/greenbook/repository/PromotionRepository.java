package com.sieuvjp.greenbook.repository;

import com.sieuvjp.greenbook.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    Optional<Promotion> findByCode(String code);

    boolean existsByCode(String code);

    List<Promotion> findByIsActive(boolean isActive);

    @Query("SELECT p FROM Promotion p WHERE p.isActive = true AND p.startDate <= ?1 AND p.endDate >= ?1")
    List<Promotion> findActivePromotions(LocalDate date);

    @Query("SELECT p FROM Promotion p WHERE p.endDate < ?1")
    List<Promotion> findExpiredPromotions(LocalDate date);

    @Query("SELECT p FROM Promotion p WHERE p.startDate > ?1")
    List<Promotion> findUpcomingPromotions(LocalDate date);
}