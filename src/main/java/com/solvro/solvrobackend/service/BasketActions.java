package com.solvro.solvrobackend.service;

import com.solvro.solvrobackend.dto.ServiceResultDto;

import java.util.UUID;

public interface BasketActions {
    ServiceResultDto addItem(UUID basketId, UUID itemId, int itemQuantity);

    ServiceResultDto deleteItem(UUID basketId, UUID itemId);

    ServiceResultDto changeAmountOfProduct(UUID basketId, UUID itemId, int newQuantity);
}
