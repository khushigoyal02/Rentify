package com.example.airbnb_clone.thread;

import com.example.airbnb_clone.entity.InventoryEntity;
import com.example.airbnb_clone.entity.Property;
import com.example.airbnb_clone.helper.PropertyHelper;
import com.example.airbnb_clone.repo.InventoryRepo;
import com.example.airbnb_clone.repo.PropertyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Slf4j
public class InventoryGenerationThread implements Runnable{
    private final PropertyHelper propertyHelper;
    private final PropertyRepository propertyRepository;
    private final InventoryRepo inventoryRepo;

    @Override
    public void run() {
        LocalDateTime date=LocalDateTime.now();

        List<Property> list=propertyRepository.findAll();
        for (Property property : list){
            Optional<InventoryEntity> optionalInventory=inventoryRepo.findByDateAndPropertyId(date, property.getId());
            if (optionalInventory.isEmpty()) {
                propertyHelper.createInventoryFor90Days(property, date);
                log.info("Inventory generated for property id: "+property.getId());
            }
        }
    }
}
