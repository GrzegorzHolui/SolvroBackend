package com.solvro.solvrobackend.service;

import com.solvro.solvrobackend.model.Basket;
import com.solvro.solvrobackend.model.Item;

import java.util.List;
import java.util.UUID;

interface BasketAndItemValidator {

    List<BasketAndItemValidatorMessage> validate(UUID basketId, UUID itemId);

    boolean isItemInBasket(Basket basketId, Item itemId);

    boolean isBasketAndItemAfterValidationAcceptable(List<BasketAndItemValidatorMessage> validatorMessage);

}
