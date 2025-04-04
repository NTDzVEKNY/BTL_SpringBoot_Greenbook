package com.sieuvjp.greenbook.service;

import com.sieuvjp.greenbook.entity.Promotion;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PromotionService {
    List<Promotion> findAll();
    Optional<Promotion> findById(Long id);
    Optional<Promotion> findByCode(String code);
    Promotion save(Promotion promotion);
    void deleteById(Long id);
    boolean existsByCode(String code);
    List<Promotion> findActivePromotions();
    List<Promotion> findExpiredPromotions();
    List<Promotion> findUpcomingPromotions();
    void toggleActiveStatus(Long promotionId);
    double calculateDiscountAmount(Promotion promotion, double originalPrice);
}