package com.example.airbnb_clone.controller;

import com.example.airbnb_clone.request.CreatePropertyRequest;
import com.example.airbnb_clone.service.BookingService;
import com.example.airbnb_clone.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/host")
@RequiredArgsConstructor
public class HostController {

    private final PropertyService propertyService;
    private final BookingService bookingService;

    @PostMapping("/property-listing")
    ResponseEntity<?> create(@RequestBody CreatePropertyRequest request, @RequestHeader Long userId) {
        return ResponseEntity.ok(propertyService.createProperty(request, userId));
    }

    @PostMapping(value = "/photos-upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<?> upload(@RequestParam Long propertyId, @RequestParam("files") List<MultipartFile> photos) {
        return ResponseEntity.ok(propertyService.uploadPropertyImages(propertyId, photos));
    }

    @GetMapping("/hosted-properties")
    ResponseEntity<?> getHostedProperties(@RequestHeader Long userId) {
        return ResponseEntity.ok(propertyService.fetchHostedProperties(userId));
    }

    @GetMapping("/export-data")
    ResponseEntity<?> export(@RequestParam Long propertyId) {
        return ResponseEntity.ok(bookingService.export(propertyId));
    }
}
