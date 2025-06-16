package com.practice.librarysystem.book;

import com.practice.librarysystem.author.Author;
import com.practice.librarysystem.category.Category;
import com.practice.librarysystem.reservation.Reservation;
import com.practice.librarysystem.review.Review;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "image_src")
    String imageSource;

    @OneToMany(mappedBy = "book")
    final List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    final List<Review> reviews = new ArrayList<>();
}
