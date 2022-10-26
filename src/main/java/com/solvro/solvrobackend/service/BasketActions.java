package com.solvro.solvrobackend.service;

import com.solvro.solvrobackend.dto.ServiceResultDto;

import java.util.UUID;

public interface BasketActions {
    ServiceResultDto addItem(String basketHash, String itemHash, int itemQuantity);

    ServiceResultDto deleteItem(String basketHash, String itemHash);


    ServiceResultDto changeAmountOfProduct(String basketHash, String itemHash, int newQuantity);
}
