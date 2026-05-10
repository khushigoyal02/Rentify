package com.example.airbnb_clone.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreatePropertyRequest {
    private String title;
    private String description;

    private Long propertyTypeId;
    private Long roomTypeId;

    private String addressLine;
    private Long cityId;

    private Integer maximumGuests;
    private Integer bedroomsCount;
    private Integer bedsCount;
    private Integer bathroomsCount;

    private BigDecimal weekdayBasePrice;
    private BigDecimal weekendBasePrice;

    private List<Long> amenityIds;
//    private Boolean instantBook;
}
