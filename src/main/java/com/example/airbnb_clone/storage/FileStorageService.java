package com.example.airbnb_clone.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileStorageService {
    void uploadImages(String path, List<MultipartFile> files) throws IOException;
}
