package com.practice.librarysystem.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.practice.librarysystem.reservation.ReservationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ReservationDTO {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @NotNull(message = "Start date is required")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;

    @NotNull(message = "Due date is required")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dueDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime returnDate;

    @NotNull(message = "Status is required")
    private ReservationStatus status;

    @NotNull(message = "Reserver ID is required")
    private Long reserverId;

    @NotNull(message = "Book ID is required")
    private Long bookId;

    // For displaying additional info (optional)
    private String reserverName;
    private String bookTitle;
    private String bookAuthor;
    private boolean overdue;

    public ReservationDTO() {}

    public ReservationDTO(Long id, LocalDateTime createdAt, LocalDateTime startDate,
                          LocalDateTime dueDate, LocalDateTime returnDate,
                          ReservationStatus status, Long reserverId, Long bookId) {
        this.id = id;
        this.createdAt = createdAt;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
        this.reserverId = reserverId;
        this.bookId = bookId;
    }

}
