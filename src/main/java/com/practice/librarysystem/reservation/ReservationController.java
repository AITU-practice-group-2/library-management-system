package com.practice.librarysystem.reservation;

import com.practice.librarysystem.reservation.dto.CreateReservationDTO;
import com.practice.librarysystem.reservation.dto.ReservationDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")
@CrossOrigin(origins = "*")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // Create a new reservation
    @PostMapping
    public ResponseEntity<?> createReservation(@Valid @RequestBody CreateReservationDTO createDTO) {
        try {
            // Check if user can make reservation
            if (!reservationService.canUserMakeReservation(createDTO.getReserverId(), createDTO.getBookId())) {
                return ResponseEntity.badRequest()
                        .body("User cannot make reservation for this book");
            }

            Long reserverId = createDTO.getReserverId();
            Long bookId = createDTO.getBookId();

            ReservationDTO reservation = reservationService.createReservation(createDTO, reserverId, bookId);
            return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating reservation: " + e.getMessage());
        }
    }

    // Get all reservations
    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        try {
            List<ReservationDTO> reservations = reservationService.getAllReservations();
            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get reservation by ID
    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id) {
        try {
            Optional<ReservationDTO> reservation = reservationService.getReservationById(id);
            return reservation.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get reservations by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByUser(@PathVariable Long userId) {
        try {
            List<ReservationDTO> reservations = reservationService.getReservationsByUser(userId);
            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get active reservations by user
    @GetMapping("/user/{userId}/active")
    public ResponseEntity<List<ReservationDTO>> getActiveReservationsByUser(@PathVariable Long userId) {
        try {
            List<ReservationDTO> reservations = reservationService.getActiveReservationsByUser(userId);
            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get reservations by book
    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByBook(@PathVariable Long bookId) {
        try {
            List<ReservationDTO> reservations = reservationService.getReservationsByBook(bookId);
            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get reservations by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByStatus(@PathVariable ReservationStatus status) {
        try {
            List<ReservationDTO> reservations = reservationService.getReservationsByStatus(status);
            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Return a book
    @PutMapping("/{id}/return")
    public ResponseEntity<?> returnBook(@PathVariable Long id) {
        try {
            ReservationDTO reservation = reservationService.returnBook(id);
            return ResponseEntity.ok(reservation);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error returning book: " + e.getMessage());
        }
    }

    // Cancel a reservation
    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelReservation(@PathVariable Long id) {
        try {
            ReservationDTO reservation = reservationService.cancelReservation(id);
            return ResponseEntity.ok(reservation);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error cancelling reservation: " + e.getMessage());
        }
    }

    // Extend due date
    @PutMapping("/{id}/extend")
    public ResponseEntity<?> extendDueDate(@PathVariable Long id,
                                           @RequestParam LocalDateTime newDueDate) {
        try {
            ReservationDTO reservation = reservationService.extendDueDate(id, newDueDate);
            return ResponseEntity.ok(reservation);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error extending due date: " + e.getMessage());
        }
    }

    // Get overdue reservations
    @GetMapping("/overdue")
    public ResponseEntity<List<ReservationDTO>> getOverdueReservations() {
        try {
            List<ReservationDTO> reservations = reservationService.getOverdueReservations();
            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get reservations due soon
    @GetMapping("/due-soon")
    public ResponseEntity<List<ReservationDTO>> getReservationsDueSoon(@RequestParam(defaultValue = "7") int days) {
        try {
            List<ReservationDTO> reservations = reservationService.getReservationsDueSoon(days);
            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Mark overdue reservations (admin endpoint)
    @PutMapping("/mark-overdue")
    public ResponseEntity<String> markOverdueReservations() {
        try {
            reservationService.markOverdueReservations();
            return ResponseEntity.ok("Overdue reservations marked successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error marking overdue reservations: " + e.getMessage());
        }
    }

    // Update reservation
    @PutMapping("/{id}")
    public ResponseEntity<?> updateReservation(@PathVariable Long id,
                                               @Valid @RequestBody ReservationDTO reservationDTO) {
        try {
            Long reserverId = reservationDTO.getReserverId();
            Long bookId = reservationDTO.getBookId();

            ReservationDTO updatedReservation = reservationService.updateReservation(id, reservationDTO, reserverId, bookId);


            return ResponseEntity.ok(updatedReservation);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating reservation: " + e.getMessage());
        }
    }

    // Delete reservation
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id) {
        try {
            reservationService.deleteReservation(id);
            return ResponseEntity.ok("Reservation deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting reservation: " + e.getMessage());
        }
    }

    // Check if user can make reservation
    @GetMapping("/can-reserve")
    public ResponseEntity<Boolean> canUserMakeReservation(@RequestParam Long userId,
                                                          @RequestParam Long bookId) {
        try {
            boolean canReserve = reservationService.canUserMakeReservation(userId, bookId);
            return ResponseEntity.ok(canReserve);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get reservation statistics
    @GetMapping("/stats")
    public ResponseEntity<ReservationService.ReservationStats> getReservationStats() {
        try {
            ReservationService.ReservationStats stats = reservationService.getReservationStats();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}