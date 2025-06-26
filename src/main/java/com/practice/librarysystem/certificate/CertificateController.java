package com.practice.librarysystem.certificate;

import com.practice.librarysystem.exception.NotFoundException;
import com.practice.librarysystem.exception.ValidationException;
import com.practice.librarysystem.reservation.Reservation;
import com.practice.librarysystem.reservation.ReservationRepository;
import com.practice.librarysystem.reservation.ReservationStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.practice.librarysystem.util.RequestConstants.getClientIp;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor

@Slf4j
@RestController
@RequestMapping("/certificates")
public class CertificateController {

    ReservationRepository reservationRepository;

    @GetMapping
    public void generateReservationCertificate(
            @RequestParam(name = "reservation") Long reservationId,
            HttpServletResponse response,
            HttpServletRequest httpServletRequest
    ) {
        log.info("Endpoint GET: /certificates?reservation={} was accessed by IP:{}", reservationId, getClientIp(httpServletRequest));

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Reservation with id: %d not found", reservationId)));

        if (reservation.getStatus() != ReservationStatus.ACTIVE) {
            throw new ValidationException("Reservation must be active to get certificate!");
        }

        try {
            byte[] pdfBytes = PdfCertificateGenerator.generateReservationCertificate(
                    reservation);

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=reservation_certificate.pdf");
            response.setContentLength(pdfBytes.length);
            response.getOutputStream().write(pdfBytes);
            response.getOutputStream().flush();

        } catch (Exception e) {
            throw new ValidationException("Failed to generate certificate");
        }
    }
}
