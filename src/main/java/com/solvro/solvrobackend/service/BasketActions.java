package com.solvro.solvrobackend.service;

import com.solvro.solvrobackend.dto.ServiceChangeQuantityResultDto;
import com.solvro.solvrobackend.dto.ServiceCrudResultDto;
import com.solvro.solvrobackend.dto.ServiceDiscountCardResultDto;
import com.solvro.solvrobackend.dto.ServiceSummaryResultDto;
import com.solvro.solvrobackend.dto.ServiceSetDeliveryTypeResultDto;
import com.solvro.solvrobackend.model.DeliveryType;

public interface BasketActions {
    ServiceCrudResultDto addItem(String itemHash, int itemQuantity);

    ServiceCrudResultDto deleteItem(String itemHash);

    ServiceChangeQuantityResultDto changeAmountOfProduct(String itemHash, int newQuantity);

    ServiceSetDeliveryTypeResultDto setDeliveryType(DeliveryType deliveryType);

    ServiceDiscountCardResultDto addDiscount(String discountCardHash);

    ServiceSummaryResultDto getInformationAboutBasket();

}