package com.sieuvjp.greenbook.dto;

import com.sieuvjp.greenbook.entity.Order;
import com.sieuvjp.greenbook.entity.OrderDetail;
import com.sieuvjp.greenbook.enums.OrderStatus;
import com.sieuvjp.greenbook.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long id;

    @NotNull(message = "User is required")
    private Long userId;

    private String username;

    private Double totalAmount;

    private Double discountAmount;

    private Double finalAmount;

    @NotNull(message = "Order status is required")
    private OrderStatus status;

    private String shippingAddress;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    private String note;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completedDate;

    private List<OrderDetailDTO> orderDetails = new ArrayList<>();

    // Convert Entity to DTO
    public static OrderDTO fromEntity(Order order) {
        List<OrderDetailDTO> detailDTOs = order.getOrderDetails().stream()
                .map(OrderDetailDTO::fromEntity)
                .collect(Collectors.toList());

        return OrderDTO.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .username(order.getUser().getUsername())
                .totalAmount(order.getTotalAmount())
                .discountAmount(order.getDiscountAmount())
                .finalAmount(order.getFinalAmount())
                .status(order.getStatus())
                .shippingAddress(order.getShippingAddress())
                .paymentMethod(order.getPaymentMethod())
                .note(order.getNote())
                .orderDate(order.getOrderDate())
                .completedDate(order.getCompletedDate())
                .orderDetails(detailDTOs)
                .build();
    }

    // Convert DTO to Entity (partial, requires user to be set separately)
    public Order toEntity() {
        Order order = new Order();
        order.setId(this.id);
        order.setTotalAmount(this.totalAmount);
        order.setDiscountAmount(this.discountAmount);
        order.setFinalAmount(this.finalAmount);
        order.setStatus(this.status);
        order.setShippingAddress(this.shippingAddress);
        order.setPaymentMethod(this.paymentMethod);
        order.setNote(this.note);
        order.setOrderDate(this.orderDate != null ? this.orderDate : LocalDateTime.now());
        order.setCompletedDate(this.completedDate);

        List<OrderDetail> details = this.orderDetails.stream()
                .map(dto -> {
                    OrderDetail detail = dto.toEntity();
                    detail.setOrder(order);
                    return detail;
                })
                .collect(Collectors.toList());

        order.setOrderDetails(details);
        return order;
    }
}