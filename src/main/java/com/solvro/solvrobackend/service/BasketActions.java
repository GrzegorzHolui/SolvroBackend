package com.solvro.solvrobackend.service;

import com.solvro.solvrobackend.dto.ServiceChangeQuantityResultDto;
import com.solvro.solvrobackend.dto.ServiceCrudResultDto;
import com.solvro.solvrobackend.dto.ServiceDiscountCardResultDto;
import com.solvro.solvrobackend.dto.ServiceSummaryResultDto;
import com.solvro.solvrobackend.dto.ServiceSetDeliveryTypeResultDto;
import com.solvro.solvrobackend.model.DeliveryType;

public interface BasketActions {
    ServiceCrudResultDto addItem(String basketHash, String itemHash, int itemQuantity);

    ServiceCrudResultDto deleteItem(String basketHash, String itemHash);

    ServiceChangeQuantityResultDto changeAmountOfProduct(String basketHash, String itemHash, int newQuantity);

    ServiceSetDeliveryTypeResultDto setDeliveryType(String basketHash, DeliveryType deliveryType);

    ServiceDiscountCardResultDto addDiscount(String basketHash, String discountCardHash);

    ServiceSummaryResultDto getInformationAboutBasket(String basketHash);

}