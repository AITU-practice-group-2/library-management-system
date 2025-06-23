package com.practice.librarysystem.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // Find reservations by user
    List<Reservation> findByReserverId(Long reserverId);

    // Find reservations by book
    List<Reservation> findByBookId(Long bookId);

    // Find active reservations by user
    List<Reservation> findByReserverIdAndStatus(Long reserverId, ReservationStatus status);

    // Find active reservations by book
    List<Reservation> findByBookIdAndStatus(Long bookId, ReservationStatus status);

    // Check if user has active reservation for a book
    Optional<Reservation> findByReserverIdAndBookIdAndStatus(Long reserverId, Long bookId, ReservationStatus status);

    // Find overdue reservations
    @Query("SELECT r FROM Reservation r WHERE r.status = :status AND r.dueDate < :currentDate AND r.returnDate IS NULL")
    List<Reservation> findOverdueReservations(@Param("status") ReservationStatus status,
                                              @Param("currentDate") LocalDateTime currentDate);

    // Find reservations by status
    List<Reservation> findByStatus(ReservationStatus status);

    // Find reservations by date range
    @Query("SELECT r FROM Reservation r WHERE r.createdAt BETWEEN :startDate AND :endDate")
    List<Reservation> findByDateRange(@Param("startDate") LocalDateTime startDate,
                                      @Param("endDate") LocalDateTime endDate);

    // Count active reservations for a user
    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.reserver.id = :reserverId AND r.status = :status")
    Long countActiveReservationsByUser(@Param("reserverId") Long reserverId,
                                       @Param("status") ReservationStatus status);

    // Count active reservations for a book
    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.book.id = :bookId AND r.status = :status")
    Long countActiveReservationsByBook(@Param("bookId") Long bookId,
                                       @Param("status") ReservationStatus status);

    // Find reservations due soon (within specified days)
    @Query("SELECT r FROM Reservation r WHERE r.status = :status AND r.dueDate BETWEEN :startDate AND :endDate AND r.returnDate IS NULL")
    List<Reservation> findReservationsDueSoon(@Param("status") ReservationStatus status,
                                              @Param("startDate") LocalDateTime startDate,
                                              @Param("endDate") LocalDateTime endDate);
}