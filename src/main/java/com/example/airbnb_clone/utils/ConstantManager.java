package com.example.airbnb_clone.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ConstantManager {
    public final Integer PROPERTY_DRAFT=6001;
    public final Integer PROPERTY_ACTIVE=6002;
    public final Integer PROPERTY_INACTIVE=6003;

    public final Integer BOOKING_PENDING=7001;
    public final Integer BOOKING_CONFIRMED=7002;
    public final Integer BOOKING_EXPIRED=7009;

    public final Integer PAYMENT_INITIATED=8001;
    public final Integer PAYMENT_SUCCESS=8002;
    public final Integer PAYMENT_FAILED=8003;
}
