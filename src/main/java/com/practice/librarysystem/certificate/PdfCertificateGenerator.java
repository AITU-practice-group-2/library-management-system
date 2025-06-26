package com.practice.librarysystem.certificate;

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.practice.librarysystem.reservation.Reservation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class PdfCertificateGenerator {

    public static byte[] generateReservationCertificate(Reservation reservation) throws Exception {
        Long userId = reservation.getReserver().getId();
        Long bookId = reservation.getBook().getId();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, outputStream);

        document.open();

        // Colors
        BaseColor brandPurple = new BaseColor(128, 90, 213);   // #805AD5
        BaseColor lightLavender = new BaseColor(245, 243, 255); // #f5f3ff
        BaseColor darkGreen = new BaseColor(0, 128, 0);

        // Fonts
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD, brandPurple);
        Font brandFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.DARK_GRAY);
        Font fieldFont = new Font(Font.FontFamily.HELVETICA, 14, Font.NORMAL, BaseColor.BLACK);
        Font labelFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, brandPurple);

        // Title
        Paragraph title = new Paragraph("BookTrack", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        // Brand Name
        Paragraph brand = new Paragraph("Reservation Certificate", brandFont);
        brand.setAlignment(Element.ALIGN_CENTER);
        brand.setSpacingAfter(30);
        document.add(brand);

        // Info Section
        String certText = String.format(
                "This certificate proves that the user %s was approved and entrusted with the storage and exploitation of the book \"%s\" in period of %s - %s, subject to the timely return of the said book.",
                reservation.getReserver().getLogin(),
                reservation.getBook().getTitle(),
                reservation.getStartDate(),
                reservation.getDueDate()
        );

        Paragraph certParagraph = new Paragraph(certText, fieldFont);
        certParagraph.setAlignment(Element.ALIGN_JUSTIFIED);
        certParagraph.setSpacingBefore(20);
        certParagraph.setSpacingAfter(30);
        document.add(certParagraph);

        // Generate QR code (text can be customized — example below)
        String qrText = String.format("""
                Reservation Details Data
                %nReserver ID: %d
                %nBook ID: %d%nIssued At: %s""",
                userId, bookId, LocalDateTime.now());
        Image qrImage = generateQrCodeImage(qrText, 150, 150);

        // Center and add QR code
        qrImage.setAlignment(Element.ALIGN_CENTER);
        qrImage.setSpacingBefore(30);
        document.add(qrImage);

        // Footer Status
        Paragraph status = new Paragraph("✔ Reservation Confirmed",
                new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, darkGreen));
        status.setAlignment(Element.ALIGN_CENTER);
        status.setSpacingBefore(20);
        document.add(status);

        document.close();

        return outputStream.toByteArray();
    }

    private static Image generateQrCodeImage(String text, int width, int height) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.MARGIN, 1);

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? 0x000000 : 0xFFFFFF);
            }
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "PNG", baos);
        baos.flush();

        return Image.getInstance(baos.toByteArray());
    }
}
