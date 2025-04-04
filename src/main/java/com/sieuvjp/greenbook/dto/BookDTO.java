package com.sieuvjp.greenbook.dto;

import com.sieuvjp.greenbook.entity.Book;
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
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private Long id;

    @NotBlank(message = "Book title is required")
    @Size(min = 2, max = 255, message = "Book title must be between 2 and 255 characters")
    private String title;

    private String author;

    private String description;

    @NotNull(message = "Original price is required")
    @Min(value = 0, message = "Original price cannot be negative")
    private Double originalPrice;

    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock quantity cannot be negative")
    private Integer stockQuantity;

    private Integer soldQuantity;

    private String publisher;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publishedDate;

    private List<String> imageUrls = new ArrayList<>();

    @NotNull(message = "Category is required")
    private Long categoryId;

    private String categoryName;

    private boolean active;

    // Convert Entity to DTO
    public static BookDTO fromEntity(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .description(book.getDescription())
                .originalPrice(book.getOriginalPrice())
                .stockQuantity(book.getStockQuantity())
                .soldQuantity(book.getSoldQuantity())
                .publisher(book.getPublisher())
                .publishedDate(book.getPublishedDate())
                .imageUrls(book.getImageUrls() != null ? book.getImageUrls() : new ArrayList<>())
                .categoryId(book.getCategory() != null ? book.getCategory().getId() : null)
                .categoryName(book.getCategory() != null ? book.getCategory().getName() : null)
                .active(book.isActive())
                .build();
    }

    // Convert DTO to Entity (partial, requires category to be set separately)
    public Book toEntity() {
        Book book = new Book();
        book.setId(this.id);
        book.setTitle(this.title);
        book.setAuthor(this.author);
        book.setDescription(this.description);
        book.setOriginalPrice(this.originalPrice);
        book.setStockQuantity(this.stockQuantity);
        book.setSoldQuantity(this.soldQuantity != null ? this.soldQuantity : 0);
        book.setPublisher(this.publisher);
        book.setPublishedDate(this.publishedDate);
        book.setImageUrls(this.imageUrls);
        book.setActive(this.active);
        return book;
    }
}