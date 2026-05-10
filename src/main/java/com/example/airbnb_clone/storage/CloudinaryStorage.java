package com.example.airbnb_clone.storage;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Profile("prod")
public class CloudinaryStorage implements FileStorageService{

    private final Cloudinary cloudinary;

    @Override
    public void uploadImages(String path, List<MultipartFile> files) throws IOException {
        for (MultipartFile file : files) {
            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    Map.of(
                            "folder", path
                    )
            );
        }
    }
}
