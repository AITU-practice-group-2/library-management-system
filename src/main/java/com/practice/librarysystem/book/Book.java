package com.practice.librarysystem.book;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String title;

    String description;

    @Column(name = "publication_year")
    int publicationYear;

    int pages;

    int available;

    //TODO: replace int's with entity objects
    @Column(name = "author_id")
    int authorId;

    @Column(name = "category_id")
    int categoryId;
}
