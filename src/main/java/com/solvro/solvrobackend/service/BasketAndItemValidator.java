package com.solvro.solvrobackend.service;

import com.solvro.solvrobackend.model.Basket;
import com.solvro.solvrobackend.model.BasketItem;
import com.solvro.solvrobackend.model.Item;

import java.util.List;
import java.util.Optional;

interface BasketAndItemValidator {
    List<BasketAndItemValidatorMessage> validateItem(String itemId);

    Optional<BasketItem> findFirstProductInBasketItem(Basket basket, Item item);

    boolean isBasketAndItemAfterValidationAcceptable(List<BasketAndItemValidatorMessage> validatorMessage);
}
