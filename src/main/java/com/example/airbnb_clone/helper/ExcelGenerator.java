package com.example.airbnb_clone.helper;

import com.example.airbnb_clone.entity.Booking;
import com.example.airbnb_clone.entity.BookingStatus;
import com.example.airbnb_clone.entity.User;
import com.example.airbnb_clone.repo.BookingStatusRepo;
import com.example.airbnb_clone.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class ExcelGenerator {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookingStatusRepo bookingStatusRepo;

    public ByteArrayInputStream generateExcel(List<Booking> bookings) {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Bookings");

            Row headerRow = sheet.createRow(0);
            String[] headers = {
                    "Booking ID", "Guest Name", "Guest Email",
                    "Check In", "Check Out", "No. of Days",
                    "Total Price", "Status"
            };

            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            // Data
            int rowIdx = 1;
            for (Booking booking : bookings) {
                Row row = sheet.createRow(rowIdx++);

                Optional<User> guest = userRepository.findById(booking.getGuestId());
                Optional<BookingStatus> status = bookingStatusRepo.findById(booking.getStatusId());

                row.createCell(0).setCellValue(booking.getId());
                row.createCell(1).setCellValue(guest.get().getName());
                row.createCell(2).setCellValue(guest.get().getEmail());
                row.createCell(3).setCellValue(booking.getCheckIn().toString());
                row.createCell(4).setCellValue(booking.getCheckOut().toString());
                row.createCell(5).setCellValue(booking.getDays());
                row.createCell(6).setCellValue(booking.getTotalPrice().doubleValue());
                row.createCell(7).setCellValue(status.get().getName());
            }

            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            workbook.close();

            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            log.error("error in generating file");
            return null;
        }
    }
}
