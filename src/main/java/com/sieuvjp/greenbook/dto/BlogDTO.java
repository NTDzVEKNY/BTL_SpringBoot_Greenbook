package com.sieuvjp.greenbook.dto;

import com.sieuvjp.greenbook.entity.Blog;
import com.sieuvjp.greenbook.enums.BlogStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogDTO {

    private Long id;

    @NotBlank(message = "Blog title is required")
    @Size(min = 5, max = 200, message = "Blog title must be between 5 and 200 characters")
    private String title;

    @NotBlank(message = "Blog content is required")
    private String content;

    private String imageUrl;

    @NotNull(message = "User is required")
    private Long userId;

    private String authorName;

    @NotNull(message = "Blog status is required")
    private BlogStatus status;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedDate;

    private String publishedDateFormatted;

    // Convert Entity to DTO
    public static BlogDTO fromEntity(Blog blog) {
        return BlogDTO.builder()
                .id(blog.getId())
                .title(blog.getTitle())
                .content(blog.getContent())
                .imageUrl(blog.getImageUrl())
                .userId(blog.getUser() != null ? blog.getUser().getId() : null)
                .authorName(blog.getUser() != null ? blog.getUser().getFullName() : "Unknown")
                .status(blog.getStatus())
                .publishedDate(blog.getPublishedDate())
                .publishedDateFormatted(blog.getPublishedDate() != null ?
                        blog.getPublishedDate().toString().replace("T", " ").substring(0, 16) : null)
                .build();
    }

    // Convert DTO to Entity (partial, requires user to be set separately)
    public Blog toEntity() {
        Blog blog = new Blog();
        blog.setId(this.id);
        blog.setTitle(this.title);
        blog.setContent(this.content);
        blog.setImageUrl(this.imageUrl);
        blog.setStatus(this.status);
        blog.setPublishedDate(this.publishedDate);
        return blog;
    }
}