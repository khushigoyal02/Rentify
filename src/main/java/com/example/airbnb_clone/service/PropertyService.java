package com.example.airbnb_clone.service;

import com.example.airbnb_clone.request.CreatePropertyRequest;
import com.example.airbnb_clone.request.FetchPropertyRequest;
import com.example.airbnb_clone.response.GenericResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PropertyService {
    GenericResponse<?> createProperty(CreatePropertyRequest request, Long userId);
    GenericResponse<?> uploadPropertyImages(Long propertyId, List<MultipartFile> photos);
    GenericResponse<?> searchAllProperties(FetchPropertyRequest request);
    GenericResponse<?> fetchHostedProperties(Long hostId);
}
