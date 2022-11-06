package com.solvro.solvrobackend.service;

import com.solvro.solvrobackend.Repository.BasketRepository;
import com.solvro.solvrobackend.Repository.ItemRepository;
import com.solvro.solvrobackend.model.Basket;
import com.solvro.solvrobackend.model.BasketItem;
import com.solvro.solvrobackend.model.Item;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
class BasketItemQuantityChanger {

    Basket basket;
    BasketRepository basketItemRepository;
    ItemRepository itemRepository;
    BasketAndItemValidator basketAndItemValidator;

    private final String NO_PRODUCT_IN_BASKET_MESSAGE = "This product is not in your basket";

    BasketItem changeQuantity(String itemHash, int newQuantity, List<String> validatorMessage) {
        Item productToChangeQuantity = itemRepository.findFirstByProductHash(itemHash).get();
        if (basketAndItemValidator.findFirstProductInBasketItem(basket, productToChangeQuantity).isEmpty()) {
            addMessageToValidatorMessage(validatorMessage, NO_PRODUCT_IN_BASKET_MESSAGE);
            return null;
        }
        for (BasketItem item : basket.getItemList()) {
            if (item.getItem().equals(productToChangeQuantity)) {
                item.setQuantity(newQuantity);
                basketItemRepository.save(basket);
                return item;
            }
        }
        return null;
    }

    private void addMessageToValidatorMessage(List<String> validatorMessage, String message) {
        validatorMessage.removeAll(validatorMessage);
        validatorMessage.add(message);
    }
}