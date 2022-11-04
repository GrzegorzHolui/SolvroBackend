package com.solvro.solvrobackend.dto;

import com.solvro.solvrobackend.model.BasketItem;

import java.util.List;


public record ServiceCrudResultDto(List<String> message, BasketItem basketItem) {
    public BasketItem basketItem() {
        return basketItem;
    }
}
