package com.solvro.solvrobackend.dto;

import com.solvro.solvrobackend.model.DeliveryType;

import java.util.List;

public record ServiceSetDeliveryTypeResultDto(List<String> message, DeliveryType deliveryType) {

}
