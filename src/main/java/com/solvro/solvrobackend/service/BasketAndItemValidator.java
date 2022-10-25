package service;

import entity.Basket;
import entity.Item;

import java.util.List;
import java.util.UUID;

interface BasketAndItemValidator {

    List<BasketAndItemValidatorMessage> validate(UUID basketId, UUID itemId);

    boolean isItemInBasket(Basket basketId, Item itemId);

}
