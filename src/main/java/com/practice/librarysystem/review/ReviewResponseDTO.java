package com.practice.librarysystem.review;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ReviewResponseDTO {
    private Long id;
    private String comment;
    private byte rating;
    private String bookTitle;
    private String userName;
    private Long userId;
    private LocalDateTime createdAt;
}
