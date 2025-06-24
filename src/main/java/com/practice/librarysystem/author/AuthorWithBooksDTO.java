package com.practice.librarysystem.author;

import com.practice.librarysystem.book.dto.BookShortResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorWithBooksDTO {
    private Long id;
    private String name;
    private String bio;
    private List<BookShortResponse> books;
} 