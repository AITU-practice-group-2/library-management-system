package com.practice.librarysystem.reservation;

import com.practice.librarysystem.exception.ValidationException;
import com.practice.librarysystem.util.PdfCertificateGenerator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/certificates")
@RequiredArgsConstructor
public class CertificateController {

    @GetMapping("/reservation")
    public void generateReservationCertificate(
            @RequestParam Long userId,
            @RequestParam Long bookId,
            HttpServletResponse response
    ) {
        try {
            byte[] pdfBytes = PdfCertificateGenerator.generateReservationCertificate(userId, bookId);

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
