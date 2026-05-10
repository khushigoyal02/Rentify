package com.example.airbnb_clone.storage;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Component
@Profile("dev")
public class LocalStorage implements FileStorageService{

    @Override
    public void uploadImages(String path, List<MultipartFile> files) throws IOException {
        Path dirPath = Paths.get(path);
        Files.createDirectories(dirPath);

        for (MultipartFile file : files){
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = dirPath.resolve(filename);
            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );
        }
    }
}
