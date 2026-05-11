package com.example.airbnb_clone.service.impl;

import com.example.airbnb_clone.entity.City;
import com.example.airbnb_clone.entity.Property;
import com.example.airbnb_clone.entity.PropertyAmenity;
import com.example.airbnb_clone.entity.PropertyAmenityId;
import com.example.airbnb_clone.helper.PropertyHelper;
import com.example.airbnb_clone.repo.CityRepository;
import com.example.airbnb_clone.repo.PropertyAmenityRepo;
import com.example.airbnb_clone.repo.PropertyRepository;
import com.example.airbnb_clone.request.CreatePropertyRequest;
import com.example.airbnb_clone.request.FetchPropertyRequest;
import com.example.airbnb_clone.request.PropertyProjection;
import com.example.airbnb_clone.response.GenericResponse;
import com.example.airbnb_clone.service.PropertyService;
import com.example.airbnb_clone.utils.ConstantManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PropertyServiceImpl implements PropertyService {
    private final PropertyRepository propertyRepository;
    private final PropertyAmenityRepo propertyAmenityRepo;
    private final CityRepository cityRepository;
    private final PropertyHelper propertyHelper;

    @Value("${file.upload.path}")
    private String basePath;

    public GenericResponse<?> createProperty(CreatePropertyRequest request, Long userId) {
        Property property = Property.builder().propertyTypeId(request.getPropertyTypeId())
                .roomTypeId(request.getRoomTypeId()).addressLine(request.getAddressLine())
                .bathroomsCount(request.getBathroomsCount())
                .cityId(request.getCityId()).bedroomsCount(request.getBedroomsCount()).hostId(userId)
                .bedsCount(request.getBedsCount()).maximumGuests(request.getMaximumGuests())
                .bedroomsCount(request.getBedroomsCount())
                .title(request.getTitle()).description(request.getDescription()).amenityIds(request.getAmenityIds())
                .weekdayBasePrice(request.getWeekdayBasePrice())
                .weekendBasePrice(request.getWeekendBasePrice())
                .statusId(ConstantManager.PROPERTY_DRAFT).build();
        Property property1 = propertyRepository.save(property);

        for (Long amenityId : request.getAmenityIds()) {
            PropertyAmenityId id = new PropertyAmenityId(property1.getId(), amenityId);

            PropertyAmenity row = PropertyAmenity.builder().id(id).property(property1).build();
            propertyAmenityRepo.save(row);
        }
        return GenericResponse.success(null);
    }

    public GenericResponse<?> uploadPropertyImages(Long propertyId, List<MultipartFile> photos) {
        Optional<Property> optionalProperty = propertyRepository.findById(propertyId);
        if (optionalProperty.isEmpty()) {
            throw new IllegalArgumentException("Property not found");
        }

        // function call
        String dirPath=basePath+'/'+"PROP"+propertyId;
        try {
            propertyHelper.uploadImages(dirPath, photos);
        } catch (Exception e){
            throw new RuntimeException("Image Upload Failed");
        }

        Property property=optionalProperty.get();
        property.setFolderPath(dirPath);
        property.setStatusId(ConstantManager.PROPERTY_ACTIVE);
        propertyRepository.save(property);
        return GenericResponse.success(null);
    }

    public GenericResponse<?> searchAllProperties(FetchPropertyRequest request) {
        Map<String,Object> responseMap=new HashMap<>();
        Optional<City> city = cityRepository.findById(request.getCityId());
        if (city.isEmpty()) {
            throw new IllegalArgumentException("City not found");
        }
        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new IllegalArgumentException("Invalid Date");
        }
        List<PropertyProjection> propertyCards = propertyRepository.search(request.getCityId(), request.getStartDate(),
                request.getEndDate());
        responseMap.put("propertyCards", propertyCards);
        return GenericResponse.success(responseMap);
    }

    public GenericResponse<?> fetchHostedProperties(Long hostId) {
        Map<String,Object> responseMap=new HashMap<>();
        List<Property> properties = propertyRepository.findAllByHostId(hostId);
        responseMap.put("hostedProperties",properties);
        return GenericResponse.success(responseMap);
    }
}
