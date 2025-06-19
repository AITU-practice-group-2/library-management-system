package com.practice.librarysystem.book.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookShortResponse {
    Long id;
    String title;
    String description;
    Boolean available;
    String author;
    String image;
}
