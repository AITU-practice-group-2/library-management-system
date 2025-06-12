package com.practice.librarysystem.book.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewBookRequest {
    @NotBlank
    String title;

    @NotBlank
    String description;

    @NotNull
    Integer publicationYear;

    @NotNull
    @Positive
    Integer pages;

    @NotNull
    @PositiveOrZero
    Integer available;

    @NotNull
    Long author;

    @NotNull
    Long category;
}
