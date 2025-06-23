package com.practice.librarysystem.book.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookFullResponse {
    Long id;

    String title;

    String description;

    Integer publicationYear;

    Integer pages;

    Integer available;

    String author;

    String category;

    String image;

    Integer views;
}
