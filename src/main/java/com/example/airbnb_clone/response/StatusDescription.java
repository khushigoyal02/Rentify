package com.example.airbnb_clone.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StatusDescription {
    Integer statusCode;
    String statusMessage;
}
