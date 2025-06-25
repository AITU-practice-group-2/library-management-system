package com.practice.librarysystem.util;

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class PdfCertificateGenerator {

    public static byte[] generateReservationCertificate(Long userId, Long bookId) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, outputStream);

        document.open();

        // Colors
        BaseColor brandPurple = new BaseColor(128, 90, 213);   // #805AD5
        BaseColor lightLavender = new BaseColor(245, 243, 255); // #f5f3ff
        BaseColor darkGreen = new BaseColor(28, 69, 50);       // #1C4532

        // Fonts
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD, brandPurple);
        Font brandFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.DARK_GRAY);
        Font fieldFont = new Font(Font.FontFamily.HELVETICA, 14, Font.NORMAL, BaseColor.BLACK);
        Font labelFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, brandPurple);

        // Title
        Paragraph title = new Paragraph("Reservation Certificate", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        // Brand Name
        Paragraph brand = new Paragraph("BookTrack", brandFont);
        brand.setAlignment(Element.ALIGN_CENTER);
        brand.setSpacingAfter(30);
        document.add(brand);

        // Info Section
        PdfPTable table = new PdfPTable(2);
        table.setWidths(new int[]{1, 2});
        table.setSpacingBefore(20);
        table.setWidthPercentage(80);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(createCell("User ID:", labelFont));
        table.addCell(createCell(String.valueOf(userId), fieldFont));

        table.addCell(createCell("Book ID:", labelFont));
        table.addCell(createCell(String.valueOf(bookId), fieldFont));

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        table.addCell(createCell("Issued At:", labelFont));
        table.addCell(createCell(timestamp, fieldFont));

        document.add(table);

        // Generate QR code (text can be customized — example below)
        String qrText = String.format("Reservation Certificate%nUser ID: %d%nBook ID: %d%nIssued At: %s",
                userId, bookId, timestamp);
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

    private static PdfPCell createCell(String content, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setPadding(10);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
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
