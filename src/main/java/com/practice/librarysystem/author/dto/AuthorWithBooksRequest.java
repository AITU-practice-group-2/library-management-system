package com.practice.librarysystem.author.dto;

import com.practice.librarysystem.book.dto.NewBookRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorWithBooksRequest {
    @NotBlank
    private String name;
    
    private String bio;
    
    @NotEmpty
    @Valid
    private List<NewBookRequest> books;
} 