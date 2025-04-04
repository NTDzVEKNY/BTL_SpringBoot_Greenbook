package com.sieuvjp.greenbook.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book extends BaseEntity {

    @Column(nullable = false)
    private String title;

    private String author;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "original_price", nullable = false)
    private Double originalPrice;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    @Column(name = "sold_quantity")
    private Integer soldQuantity = 0;

    private String publisher;

    @Column(name = "published_date")
    private LocalDate publishedDate;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrlJson;

    @Transient
    private List<String> imageUrls = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "is_active")
    private boolean isActive = true;

    @OneToMany(mappedBy = "book")
    private List<OrderDetail> orderDetails;

    @PostLoad
    public void loadImageUrls() {
        if (imageUrlJson != null && !imageUrlJson.isEmpty()) {
            try {
                imageUrls = new ObjectMapper().readValue(
                        imageUrlJson,
                        new TypeReference<List<String>>() {}
                );
            } catch (JsonProcessingException e) {
                // Handle exception
                imageUrls = new ArrayList<>();
            }
        }
    }

    @PrePersist
    @PreUpdate
    public void saveImageUrls() {
        if (imageUrls != null && !imageUrls.isEmpty()) {
            try {
                imageUrlJson = new ObjectMapper().writeValueAsString(imageUrls);
            } catch (JsonProcessingException e) {
                // Handle exception
                imageUrlJson = "[]";
            }
        } else {
            imageUrlJson = "[]";
        }
    }
}