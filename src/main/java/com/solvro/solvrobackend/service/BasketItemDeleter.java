package com.solvro.solvrobackend.service;

import com.solvro.solvrobackend.Repository.BasketRepository;
import com.solvro.solvrobackend.Repository.ItemRepository;
import com.solvro.solvrobackend.model.Basket;
import com.solvro.solvrobackend.model.BasketItem;
import com.solvro.solvrobackend.model.Item;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
class BasketItemDeleter {
    BasketRepository basketItemRepository;
    ItemRepository itemRepository;
    BasketAndItemValidator basketAndItemValidator;
    private static final String NO_PRODUCT_IN_BASKET = "This product is not in your basket";

    BasketItem deleteBasketItem(String basketHash, String itemHash, List<String> validatorMessage) {
        Basket currentBasket = basketItemRepository.findFirstByBasketHash(basketHash).get();
        Item productToRemove = itemRepository.findFirstByProductHash(itemHash).get();
        if (basketAndItemValidator.findFirstProductInBasketItem(currentBasket, productToRemove).isEmpty()) {
            addMessageToValidatorMessage(validatorMessage, NO_PRODUCT_IN_BASKET);
            return null;
        }
        for (BasketItem item : currentBasket.getItemList()) {
            if (item.getItem().equals(productToRemove)) {
                currentBasket.getItemList().remove(item);
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
