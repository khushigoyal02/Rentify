package com.example.airbnb_clone.helper;

import com.cloudinary.Cloudinary;
import com.example.airbnb_clone.entity.InventoryEntity;
import com.example.airbnb_clone.entity.Property;
import com.example.airbnb_clone.repo.InventoryRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class PropertyHelper {

    private final Cloudinary cloudinary;
    private final InventoryRepo inventoryRepo;

    public void store(String basePath, Long propertyId, MultipartFile file){
        try {
            String path = basePath + '/' + propertyId;
            Path dirPath = Paths.get(path);
            Files.createDirectories(dirPath);

            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = dirPath.resolve(filename);
            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (Exception e){
            log.error("Error in storing file");
        }
    }

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

    public void createInventoryFor90Days(Property property, LocalDateTime date){

        int totalUnits;
        if (property.getRoomTypeId()==30001){
            totalUnits=1;
        } else if (property.getRoomTypeId()==30002){
            totalUnits=property.getBedroomsCount();
        } else {
            totalUnits=property.getBedsCount();
        }

        for (int i=0; i<90; i++){
            BigDecimal price = BigDecimal.ZERO;
            if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                price = property.getWeekendBasePrice();
            } else
                price = property.getWeekdayBasePrice();
            InventoryEntity inventory=InventoryEntity.builder().propertyId(property.getId()).date(date)
                    .totalUnits(totalUnits).bookedUnits(0).reservedUnits(0).price(price).closed(false).build();
            date=date.plusDays(1);
            inventoryRepo.save(inventory);
        }
    }
}
