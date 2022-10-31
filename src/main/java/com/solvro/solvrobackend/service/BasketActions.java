package com.solvro.solvrobackend.service;

import com.solvro.solvrobackend.dto.ServiceResultDto;
import com.solvro.solvrobackend.model.DeliveryType;

public interface BasketActions {
    ServiceResultDto addItem(String basketHash, String itemHash, int itemQuantity);

    ServiceResultDto deleteItem(String basketHash, String itemHash);

    ServiceResultDto changeAmountOfProduct(String basketHash, String itemHash, int newQuantity);

    ServiceResultDto setDeliveryType(String basketHash, DeliveryType deliveryType);

    ServiceResultDto setDiscount(String basketHash, String discountCardHash);

    ServiceResultDto getInformationAboutBasket(String basketHash);

}