package com.practice.librarysystem.dto;
import java.time.LocalDateTime;

public class ReviewResponseDTO {
    private Long id;
    private String comment;
    private int rating;
    private String bookTitle;
    private String userName;
    private LocalDateTime createdAt;
}
