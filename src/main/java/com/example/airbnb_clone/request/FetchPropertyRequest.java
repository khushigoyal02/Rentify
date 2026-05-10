package com.example.airbnb_clone.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FetchPropertyRequest {
    @NotNull(message = "City Id is required")
    private Long cityId;
//    @Future
    @NotNull(message = "Start Date is required")
    private LocalDateTime startDate;
    @NotNull(message = "End Date is required")
    private LocalDateTime endDate;

//    @NotNull(message = "Number of adults is required")
    @Min(1)
    private Integer adults;
//    @NotNull(message = "Number of children is required")
    @Min(0)
    private Integer children;

    @Min(0)
    @Max(5)
//    @NotNull(message = "Number of infants is required")
    private Integer infants;
    @Min(0)
    @Max(5)

//    @NotNull(message = "Number of pets is required")
    private Integer pets;
}
