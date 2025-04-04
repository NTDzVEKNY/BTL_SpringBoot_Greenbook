package com.sieuvjp.greenbook.dto;

import com.sieuvjp.greenbook.entity.Promotion;
import com.sieuvjp.greenbook.enums.PromotionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromotionDTO {

    private Long id;

    @NotBlank(message = "Promotion name is required")
    @Size(min = 2, max = 100, message = "Promotion name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Promotion code is required")
    @Size(min = 2, max = 20, message = "Promotion code must be between 2 and 20 characters")
    private String code;

    @NotNull(message = "Promotion type is required")
    private PromotionType type;

    @NotNull(message = "Promotion value is required")
    @Min(value = 0, message = "Promotion value cannot be negative")
    private Double value;

    @NotNull(message = "Start date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private String description;

    private boolean active;

    private boolean current;

    // Convert Entity to DTO
    public static PromotionDTO fromEntity(Promotion promotion) {
        LocalDate now = LocalDate.now();
        boolean isCurrent = promotion.isActive() &&
                !promotion.getStartDate().isAfter(now) &&
                !promotion.getEndDate().isBefore(now);

        return PromotionDTO.builder()
                .id(promotion.getId())
                .name(promotion.getName())
                .code(promotion.getCode())
                .type(promotion.getType())
                .value(promotion.getValue())
                .startDate(promotion.getStartDate())
                .endDate(promotion.getEndDate())
                .description(promotion.getDescription())
                .active(promotion.isActive())
                .current(isCurrent)
                .build();
    }

    // Convert DTO to Entity
    public Promotion toEntity() {
        Promotion promotion = new Promotion();
        promotion.setId(this.id);
        promotion.setName(this.name);
        promotion.setCode(this.code);
        promotion.setType(this.type);
        promotion.setValue(this.value);
        promotion.setStartDate(this.startDate);
        promotion.setEndDate(this.endDate);
        promotion.setDescription(this.description);
        promotion.setActive(this.active);
        return promotion;
    }
}