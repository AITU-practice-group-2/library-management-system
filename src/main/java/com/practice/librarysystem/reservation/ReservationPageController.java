package com.practice.librarysystem.reservation;

import com.practice.librarysystem.reservation.dto.ReservationDTO;
import com.practice.librarysystem.user.User;
import com.practice.librarysystem.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ReservationPageController {

    private final ReservationService reservationService;
    private final UserRepository userRepository;

    @GetMapping("/reservation-details")
    public String getReservationPage(@RequestParam Long id,
                                     @AuthenticationPrincipal UserDetails principal,
                                     Model model) {
        if (principal == null) {
            throw new RuntimeException("User not authenticated");
        }
        String username = principal.getUsername();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ReservationDTO reservation = reservationService.getReservationById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        if (!user.getRole().name().equals("ADMIN") &&
                !reservation.getReserverId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        model.addAttribute("reservation", reservation);
        model.addAttribute("user", user);
        return "reservation-details";
    }
}
