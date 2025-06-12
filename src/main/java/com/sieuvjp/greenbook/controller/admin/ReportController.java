package com.sieuvjp.greenbook.controller.admin;

import lombok.RequiredArgsConstructor;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/report")
@RequiredArgsConstructor
public class ReportController {

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportPDF() throws IOException, DocumentException {
        // Giả lập dữ liệu doanh thu tháng này và tháng trước
        double totalRevenueCurrentMonth = 5000000;  // Tổng doanh thu tháng này
        double totalRevenuePreviousMonth = 4000000;  // Tổng doanh thu tháng trước

        // Giả lập các quyển sách bán chạy nhất trong tháng
        List<Object[]> topSellingBooks = Arrays.asList(
                new Object[]{"Sach A", 1500},
                new Object[]{"Sach B", 1300},
                new Object[]{"Sach C", 1200},
                new Object[]{"Sach D", 1100},
                new Object[]{"Sach E", 1000}
        );

        // Giả lập các danh mục bán chạy nhất trong tháng
        List<Object[]> topSellingCategories = Arrays.asList(
                new Object[]{"Danh muc A", 2000000.0},
                new Object[]{"Danh muc B", 1500000.0},
                new Object[]{"Danh muc C", 1000000.0},
                new Object[]{"Danh muc D", 500000.0},
                new Object[]{"Danh muc E", 300000.0}
        );

        // Tính tỷ lệ tăng trưởng doanh thu so với tháng trước
        double growthRate = calculateGrowthRate(totalRevenuePreviousMonth, totalRevenueCurrentMonth);

        // Tạo PDF với iText
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // Tạo font (sử dụng font mặc định không cần tiếng Việt có dấu)
        Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
        Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

        // Thêm tiêu đề
        Paragraph title = new Paragraph("BAO CAO DOANH THU", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph(" ")); // Dòng trống

        // Thông tin công ty và thời gian
        document.add(new Paragraph("Cong ty: Cong ty A", normalFont));
        document.add(new Paragraph("Ngay xuat bao cao: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), normalFont));
        document.add(new Paragraph(" "));

        // Thông tin doanh thu tổng quan
        document.add(new Paragraph("THONG TIN TONG QUAN", headerFont));
        document.add(new Paragraph("Tong doanh thu thang nay: " + String.format("%,.0f VND", totalRevenueCurrentMonth), normalFont));
        document.add(new Paragraph("Ty le tang truong: " + String.format("%.2f%%", growthRate), normalFont));
        document.add(new Paragraph(" "));

        // Bảng sách bán chạy nhất
        document.add(new Paragraph("5 QUYEN SACH BAN CHAY NHAT", headerFont));
        PdfPTable bookTable = new PdfPTable(2);
        bookTable.setWidthPercentage(100);
        bookTable.addCell(new PdfPCell(new Phrase("Ten sach", headerFont)));
        bookTable.addCell(new PdfPCell(new Phrase("So luong ban", headerFont)));

        int count = 0;
        for (Object[] book : topSellingBooks) {
            if (count++ == 5) break;
            bookTable.addCell(new PdfPCell(new Phrase(book[0].toString(), normalFont)));
            bookTable.addCell(new PdfPCell(new Phrase(book[1].toString(), normalFont)));
        }
        document.add(bookTable);
        document.add(new Paragraph(" "));

        // Bảng danh mục bán chạy nhất
        document.add(new Paragraph("5 DANH MUC BAN CHAY NHAT", headerFont));
        PdfPTable categoryTable = new PdfPTable(2);
        categoryTable.setWidthPercentage(100);
        categoryTable.addCell(new PdfPCell(new Phrase("Danh muc", headerFont)));
        categoryTable.addCell(new PdfPCell(new Phrase("Doanh thu", headerFont)));

        count = 0;
        for (Object[] category : topSellingCategories) {
            if (count++ == 5) break;
            categoryTable.addCell(new PdfPCell(new Phrase(category[0].toString(), normalFont)));
            categoryTable.addCell(new PdfPCell(new Phrase(String.format("%,.0f VND", ((Number)category[1]).doubleValue()), normalFont)));
        }
        document.add(categoryTable);
        document.add(new Paragraph(" "));

        // So sánh doanh thu
        document.add(new Paragraph("SO SANH DOANH THU", headerFont));
        PdfPTable compareTable = new PdfPTable(2);
        compareTable.setWidthPercentage(100);
        compareTable.addCell(new PdfPCell(new Phrase("Chi tiet", headerFont)));
        compareTable.addCell(new PdfPCell(new Phrase("Gia tri", headerFont)));

        compareTable.addCell(new PdfPCell(new Phrase("Doanh thu thang truoc", normalFont)));
        compareTable.addCell(new PdfPCell(new Phrase(String.format("%,.0f VND", totalRevenuePreviousMonth), normalFont)));

        compareTable.addCell(new PdfPCell(new Phrase("Doanh thu thang nay", normalFont)));
        compareTable.addCell(new PdfPCell(new Phrase(String.format("%,.0f VND", totalRevenueCurrentMonth), normalFont)));

        compareTable.addCell(new PdfPCell(new Phrase("Chenh lech", normalFont)));
        compareTable.addCell(new PdfPCell(new Phrase(String.format("%,.0f VND", (totalRevenueCurrentMonth - totalRevenuePreviousMonth)), normalFont)));

        compareTable.addCell(new PdfPCell(new Phrase("Ty le tang/giam", normalFont)));
        compareTable.addCell(new PdfPCell(new Phrase(String.format("%.2f%%", growthRate), normalFont)));

        document.add(compareTable);

        document.close();

        // Trả về PDF
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=doanh_thu_report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(outputStream.toByteArray());
    }

    // Phương thức tính tỷ lệ tăng/giảm doanh thu
    private double calculateGrowthRate(double previousRevenue, double currentRevenue) {
        return previousRevenue == 0 ? 0 : ((currentRevenue - previousRevenue) / previousRevenue) * 100;
    }
}