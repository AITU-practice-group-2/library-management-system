package com.practice.librarysystem.book.dto;

import jakarta.validation.constraints.*;
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
    @Size(max = 2027)
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
