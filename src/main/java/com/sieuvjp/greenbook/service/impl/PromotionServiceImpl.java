package com.sieuvjp.greenbook.service.impl;

import com.sieuvjp.greenbook.entity.Promotion;
import com.sieuvjp.greenbook.enums.PromotionType;
import com.sieuvjp.greenbook.exception.ResourceNotFoundException;
import com.sieuvjp.greenbook.repository.PromotionRepository;
import com.sieuvjp.greenbook.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;

    @Override
    public List<Promotion> findAll() {
        return promotionRepository.findAll();
    }

    @Override
    public Optional<Promotion> findById(Long id) {
        return promotionRepository.findById(id);
    }

    @Override
    public Optional<Promotion> findByCode(String code) {
        return promotionRepository.findByCode(code);
    }

    @Override
    @Transactional
    public Promotion save(Promotion promotion) {
        return promotionRepository.save(promotion);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        promotionRepository.deleteById(id);
    }

    @Override
    public boolean existsByCode(String code) {
        return promotionRepository.existsByCode(code);
    }

    @Override
    public List<Promotion> findActivePromotions() {
        return promotionRepository.findActivePromotions(LocalDate.now());
    }

    @Override
    public List<Promotion> findExpiredPromotions() {
        return promotionRepository.findExpiredPromotions(LocalDate.now());
    }

    @Override
    public List<Promotion> findUpcomingPromotions() {
        return promotionRepository.findUpcomingPromotions(LocalDate.now());
    }

    @Override
    @Transactional
    public void toggleActiveStatus(Long promotionId) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new ResourceNotFoundException("Promotion not found with id: " + promotionId));

        promotion.setActive(!promotion.isActive());
        promotionRepository.save(promotion);
    }

    @Override
    public double calculateDiscountAmount(Promotion promotion, double originalPrice) {
        if (promotion.getType() == PromotionType.PERCENTAGE) {
            return originalPrice * (promotion.getValue() / 100.0);
        } else { // FIXED_AMOUNT
            return Math.min(promotion.getValue(), originalPrice); // Can't discount more than the price
        }
    }
}