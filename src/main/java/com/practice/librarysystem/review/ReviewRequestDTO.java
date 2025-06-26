package com.practice.librarysystem.review;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequestDTO {

    @NotNull(message = "User ID must not be null")
    private Long userId;

    @NotNull(message = "Book ID must not be null")
    private Long bookId;

    private String comment;
    private byte rating;
}
