package com.practice.librarysystem.reservation;

import com.practice.librarysystem.book.Book;
import com.practice.librarysystem.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "reservations")
public class Reservation {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "due_date", nullable = false)
    private LocalDateTime dueDate;

    @Column(name = "return_date")
    private LocalDateTime returnDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @ManyToOne
    private User reserver;

    @ManyToOne
    private Book book;

    // Helper methods
    public boolean isOverdue() {
        return status == ReservationStatus.ACTIVE &&
                LocalDateTime.now().isAfter(dueDate) &&
                returnDate == null;
    }

    public boolean isActive() {
        return status == ReservationStatus.ACTIVE;
    }

    public boolean isReturned() {
        return status == ReservationStatus.RETURNED;
    }
}