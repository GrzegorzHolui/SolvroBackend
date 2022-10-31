package com.solvro.solvrobackend.model;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum DeliveryType {
    COURIER_DELIVERY_INPOST(BigDecimal.valueOf(20)),
    PACKAGE_LOCKER_INPOST(BigDecimal.valueOf(10)),
    MAIL_PARCEL_POCZTA_POLSKA(BigDecimal.valueOf(5));
    final BigDecimal deliveryPrice;

    DeliveryType(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }
}
