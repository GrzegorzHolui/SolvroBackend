package com.solvro.solvrobackend.controllers.RequestsDto;

import com.solvro.solvrobackend.model.DeliveryType;
import lombok.Getter;

@Getter
public class SetterDeliveryTypeRequest {
    String basketHash;
    DeliveryType deliveryType;
}