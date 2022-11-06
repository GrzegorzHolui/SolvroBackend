package com.solvro.solvrobackend.service;

import com.solvro.solvrobackend.Repository.BasketRepository;
import com.solvro.solvrobackend.Repository.ItemRepository;
import com.solvro.solvrobackend.model.Basket;
import com.solvro.solvrobackend.model.BasketItem;
import com.solvro.solvrobackend.model.Item;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
class BasketItemSaver {
    Basket basket;
    BasketRepository basketItemRepository;
    ItemRepository itemRepository;
    BasketAndItemValidator basketAndItemValidator;

    BasketItem saveBasketItem(String itemHash, int itemQuantity) {
        Item currentProduct = itemRepository.findFirstByProductHash(itemHash).get();
        BasketItem currentBasketItem = BasketItem.builder()
                .item(currentProduct)
                .quantity(itemQuantity)
                .build();
        Optional<BasketItem> productInBasketItem = basketAndItemValidator.findFirstProductInBasketItem(basket, currentProduct);
        if (productInBasketItem.isEmpty()) {
            basket.getItemList().add(currentBasketItem);
            basketItemRepository.save(basket);
            return currentBasketItem;
        } else {
            int currentQuantity = productInBasketItem.get().getQuantity();
            productInBasketItem.get().setQuantity(currentQuantity + itemQuantity);
            basketItemRepository.save(basket);
            return productInBasketItem.get();
        }
    }
}