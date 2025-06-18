package com.practice.librarysystem.book.dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateBookRequest {
    String title;

    String description;
    
    Integer publicationYear;

    @PositiveOrZero
    Integer pages;

    @PositiveOrZero
    Integer available;

    Long author;

    Long category;

    String image;
}
