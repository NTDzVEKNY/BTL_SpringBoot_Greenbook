package com.sieuvjp.greenbook.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @Column(name = "is_active")
    private boolean isActive = true;

    @OneToMany(mappedBy = "category")
    private List<Book> books;
}