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
    BasketRepository basketItemRepository;
    ItemRepository itemRepository;
    BasketAndItemValidator basketAndItemValidator;

    private final String NO_PRODUCT_IN_BASKET_MESSAGE = "This product is not in your basket";

    BasketItem changeQuantity(String basketHash, String itemHash, int newQuantity, List<String> validatorMessage) {
        Basket currentBasket = basketItemRepository.findFirstByBasketHash(basketHash).get();
        Item productToChangeQuantity = itemRepository.findFirstByProductHash(itemHash).get();
        if (basketAndItemValidator.findFirstProductInBasketItem(currentBasket, productToChangeQuantity).isEmpty()) {
            addMessageToValidatorMessage(validatorMessage, NO_PRODUCT_IN_BASKET_MESSAGE);
            return null;
        }
        for (BasketItem item : currentBasket.getItemList()) {
            if (item.getItem().equals(productToChangeQuantity)) {
                item.setQuantity(newQuantity);
                basketItemRepository.save(currentBasket);
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