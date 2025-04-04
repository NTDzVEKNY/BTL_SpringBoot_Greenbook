package com.sieuvjp.greenbook.dto;

import com.sieuvjp.greenbook.entity.OrderDetail;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDTO {

    private Long id;

    private Long orderId;

    @NotNull(message = "Book is required")
    private Long bookId;

    private String bookTitle;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Original price is required")
    @Min(value = 0, message = "Original price cannot be negative")
    private Double originalPrice;

    private Double discountedPrice;

    private Long promotionId;

    private String promotionCode;

    private Double subtotal;

    // Convert Entity to DTO
    public static OrderDetailDTO fromEntity(OrderDetail detail) {
        return OrderDetailDTO.builder()
                .id(detail.getId())
                .orderId(detail.getOrder() != null ? detail.getOrder().getId() : null)
                .bookId(detail.getBook() != null ? detail.getBook().getId() : null)
                .bookTitle(detail.getBook() != null ? detail.getBook().getTitle() : null)
                .quantity(detail.getQuantity())
                .originalPrice(detail.getOriginalPrice())
                .discountedPrice(detail.getDiscountedPrice())
                .promotionId(detail.getPromotion() != null ? detail.getPromotion().getId() : null)
                .promotionCode(detail.getPromotion() != null ? detail.getPromotion().getCode() : null)
                .subtotal(detail.getSubtotal())
                .build();
    }

    // Convert DTO to Entity (partial, requires order, book, and promotion to be set separately)
    public OrderDetail toEntity() {
        OrderDetail detail = new OrderDetail();
        detail.setId(this.id);
        detail.setQuantity(this.quantity);
        detail.setOriginalPrice(this.originalPrice);
        detail.setDiscountedPrice(this.discountedPrice);
        return detail;
    }
}