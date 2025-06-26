package com.practice.librarysystem.reservation;

import com.practice.librarysystem.book.Book;
import com.practice.librarysystem.book.BookRepository;
import com.practice.librarysystem.exception.NotFoundException;
import com.practice.librarysystem.reservation.dto.CreateReservationDTO;
import com.practice.librarysystem.reservation.dto.ReservationDTO;
import com.practice.librarysystem.user.User;
import com.practice.librarysystem.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

@Service
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    // Create a new reservation
    public ReservationDTO createReservation(CreateReservationDTO createDTO, Long reserverId, Long bookId) {
        // Check if user already has an active reservation for this book
        Optional<Reservation> existingReservation = reservationRepository
                .findByReserverIdAndBookIdAndStatus(createDTO.getReserverId(),
                        createDTO.getBookId(),
                        ReservationStatus.ACTIVE);

        if (existingReservation.isPresent()) {
            throw new IllegalStateException("User already has an active reservation for this book");
        }

        // Validate dates
        if (createDTO.getStartDate().isAfter(createDTO.getDueDate())) {
            throw new IllegalArgumentException("Start date cannot be after due date");
        }

        User reserver = findUserById(reserverId);
        Book book = findBookById(bookId);

        Reservation reservation = reservationMapper.toEntity(createDTO);
        reservation.setStatus(ReservationStatus.PENDING);


        reservation.setReserver(reserver);
        reservation.setBook(book);

        Reservation savedReservation = reservationRepository.save(reservation);

        return reservationMapper.toDTO(savedReservation);
    }

    public ReservationDTO approveReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        Book book = reservation.getBook();

        if (reservation.getStatus() != ReservationStatus.PENDING) {
            throw new IllegalStateException("Only pending reservations can be approved");
        }

        book.setAvailable(book.getAvailable() - 1);
        bookRepository.save(book);

        reservation.setStatus(ReservationStatus.ACTIVE);
        return reservationMapper.toDTO(reservation);
    }

    public ReservationDTO rejectReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        if (reservation.getStatus() != ReservationStatus.PENDING) {
            throw new IllegalStateException("Only pending reservations can be rejected");
        }

        reservation.setStatus(ReservationStatus.REJECTED);
        return reservationMapper.toDTO(reservation);
    }

    // Get reservation by ID
    public Optional<ReservationDTO> getReservationById(Long id) {
        return reservationRepository.findById(id)
                .map(reservationMapper::toDTO);
    }

    // Get all reservations
    public List<ReservationDTO> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservationMapper.toDTOList(reservations);
    }

    // Get reservations by user
    public List<ReservationDTO> getReservationsByUser(Long userId) {
        List<Reservation> reservations = reservationRepository.findByReserverId(userId);
        return reservationMapper.toDTOList(reservations);
    }

    // Get reservations by book
    public List<ReservationDTO> getReservationsByBook(Long bookId) {
        List<Reservation> reservations = reservationRepository.findByBookId(bookId);
        return reservationMapper.toDTOList(reservations);
    }

    // Get active reservations by user
    public List<ReservationDTO> getActiveReservationsByUser(Long userId) {
        List<Reservation> reservations = reservationRepository
                .findByReserverIdAndStatus(userId, ReservationStatus.ACTIVE);
        return reservationMapper.toDTOList(reservations);
    }

    // Get reservations by status
    public List<ReservationDTO> getReservationsByStatus(ReservationStatus status) {
        List<Reservation> reservations = reservationRepository.findByStatus(status);
        return reservationMapper.toDTOList(reservations);
    }

    // Return a book (complete reservation)
    public ReservationDTO returnBook(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        Book book = reservation.getBook();

        if (reservation.getStatus() != ReservationStatus.ACTIVE) {
            throw new IllegalStateException("Only active reservations can be returned");
        }

        reservation.setReturnDate(LocalDateTime.now());
        reservation.setStatus(ReservationStatus.RETURNED);

        book.setAvailable(book.getAvailable() + 1);
        bookRepository.save(book);

        Reservation savedReservation = reservationRepository.save(reservation);
        return reservationMapper.toDTO(savedReservation);
    }

    // Cancel a reservation
    public ReservationDTO cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        Book book = reservation.getBook();

        if (reservation.getStatus() != ReservationStatus.ACTIVE) {
            throw new IllegalStateException("Only active reservations can be cancelled");
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        book.setAvailable(book.getAvailable() + 1);
        bookRepository.save(book);

        Reservation savedReservation = reservationRepository.save(reservation);
        return reservationMapper.toDTO(savedReservation);
    }

    // Extend due date
    public ReservationDTO extendDueDate(Long reservationId, LocalDateTime newDueDate) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        if (reservation.getStatus() != ReservationStatus.ACTIVE) {
            throw new IllegalStateException("Only active reservations can be extended");
        }

        if (newDueDate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("New due date cannot be in the past");
        }

        reservation.setDueDate(newDueDate);

        Reservation savedReservation = reservationRepository.save(reservation);
        return reservationMapper.toDTO(savedReservation);
    }

    // Get overdue reservations
    public List<ReservationDTO> getOverdueReservations() {
        List<Reservation> overdueReservations = reservationRepository
                .findOverdueReservations(ReservationStatus.ACTIVE, LocalDateTime.now());
        return reservationMapper.toDTOList(overdueReservations);
    }

    // Mark overdue reservations
    public void markOverdueReservations() {
        List<Reservation> overdueReservations = reservationRepository
                .findOverdueReservations(ReservationStatus.ACTIVE, LocalDateTime.now());

        for (Reservation reservation : overdueReservations) {
            reservation.setStatus(ReservationStatus.OVERDUE);
        }

        reservationRepository.saveAll(overdueReservations);
    }

    // Get reservations due soon (within specified days)
    public List<ReservationDTO> getReservationsDueSoon(int days) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dueDate = now.plusDays(days);

        List<Reservation> reservations = reservationRepository
                .findReservationsDueSoon(ReservationStatus.ACTIVE, now, dueDate);
        return reservationMapper.toDTOList(reservations);
    }

    // Update reservation
    public ReservationDTO updateReservation(Long id, ReservationDTO reservationDTO, Long reserverId, Long bookId) {
        Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        reservationMapper.updateEntityFromDTO(existingReservation, reservationDTO);


        if (reserverId != null && reserverId > 0) {
            User reserver = findUserById(reserverId);
            existingReservation.setReserver(reserver);
        }

        if (bookId != null && bookId > 0) {
            Book book = findBookById(bookId);
            existingReservation.setBook(book);
        }

        reservationRepository.save(existingReservation);

        return reservationMapper.toDTO(existingReservation);
    }

    // Delete reservation
    public void deleteReservation(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new IllegalArgumentException("Reservation not found");
        }
        reservationRepository.deleteById(id);
    }

    // Check if user can make a reservation (business logic)
    public boolean canUserMakeReservation(Long userId, Long bookId) {
        // Check if user already has active reservation for this book
        Optional<Reservation> existingReservation = reservationRepository
                .findByReserverIdAndBookIdAndStatus(userId, bookId, ReservationStatus.ACTIVE);

        if (existingReservation.isPresent()) {
            return false;
        }

        // Check if user has reached maximum reservations (e.g., 5 active reservations)
        Long activeReservationsCount = reservationRepository
                .countActiveReservationsByUser(userId, ReservationStatus.ACTIVE);

        return activeReservationsCount < 5; // Maximum 5 active reservations per user
    }

    // Get reservation statistics
    public ReservationStats getReservationStats() {
        long totalReservations = reservationRepository.count();
        long activeReservations = reservationRepository.findByStatus(ReservationStatus.ACTIVE).size();
        long overdueReservations = getOverdueReservations().size();
        long returnedReservations = reservationRepository.findByStatus(ReservationStatus.RETURNED).size();

        return new ReservationStats(totalReservations, activeReservations,
                overdueReservations, returnedReservations);
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found."));
    }

    private Book findBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Book with id %d not found", id)));
    }

    // Inner class for statistics
    public static class ReservationStats {
        private final long total;
        private final long active;
        private final long overdue;
        private final long returned;

        public ReservationStats(long total, long active, long overdue, long returned) {
            this.total = total;
            this.active = active;
            this.overdue = overdue;
            this.returned = returned;
        }

        //
    }
}