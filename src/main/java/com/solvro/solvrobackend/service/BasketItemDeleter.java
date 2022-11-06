package com.solvro.solvrobackend.service;

import com.solvro.solvrobackend.Repository.BasketRepository;
import com.solvro.solvrobackend.Repository.ItemRepository;
import com.solvro.solvrobackend.model.Basket;
import com.solvro.solvrobackend.model.BasketItem;
import com.solvro.solvrobackend.model.Item;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
class BasketItemDeleter {

    Basket basket;
    BasketRepository basketItemRepository;
    ItemRepository itemRepository;
    BasketAndItemValidator basketAndItemValidator;
    private static final String NO_PRODUCT_IN_BASKET = "This product is not in your basket";

    BasketItem deleteBasketItem(String itemHash, List<String> validatorMessage) {
        Item productToRemove = itemRepository.findFirstByProductHash(itemHash).get();
        if (basketAndItemValidator.findFirstProductInBasketItem(basket, productToRemove).isEmpty()) {
            addMessageToValidatorMessage(validatorMessage, NO_PRODUCT_IN_BASKET);
            return null;
        }
        for (BasketItem item : basket.getItemList()) {
            if (item.getItem().equals(productToRemove)) {
                basket.getItemList().remove(item);
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
