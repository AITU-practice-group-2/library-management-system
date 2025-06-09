package com.practice.librarysystem.review;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ReviewResponseDTO {
    private Long id;
    private String comment;
    private int rating;
    private String bookTitle;
    private String userName;
    private LocalDateTime createdAt;
}
