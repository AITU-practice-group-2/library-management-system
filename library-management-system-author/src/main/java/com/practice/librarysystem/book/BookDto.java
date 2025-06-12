package com.practice.librarysystem.book;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookDto {
    @Size(max = 100)
    String title;

    @Size(max = 2000)
    String description;

    @NotNull
    Integer publicationYear;

    @NotNull
    Integer pages;

    @NotNull
    Integer available;

    Integer author;

    Integer category;
}
