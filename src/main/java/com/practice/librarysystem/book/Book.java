package com.practice.librarysystem.book;

import com.practice.librarysystem.author.Author;
import com.practice.librarysystem.category.Category;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;

    String description;

    @Column(name = "publication_year")
    Integer publicationYear;

    Integer pages;

    Integer available;

    @ManyToOne
    Author author;

    @ManyToOne
    Category category;
}
