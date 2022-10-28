package com.solvro.solvrobackend.service;

import com.solvro.solvrobackend.model.Basket;
import com.solvro.solvrobackend.model.BasketItem;
import com.solvro.solvrobackend.model.Item;

import java.util.List;
import java.util.Optional;

interface BasketAndItemValidator {

    List<BasketAndItemValidatorMessage> validate(String basketId, String itemId);

//    boolean isItemInBasket(Basket basket, Item item);

    Optional<BasketItem> getProductInBasketItem(Basket basket, Item item);

    boolean isBasketAndItemAfterValidationAcceptable(List<BasketAndItemValidatorMessage> validatorMessage);

}
