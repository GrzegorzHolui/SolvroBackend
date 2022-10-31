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
    BasketRepository basketItemRepository;
    ItemRepository itemRepository;
    BasketAndItemValidator basketAndItemValidator;

    BasketItem saveBasketItem(String basketHash, String itemHash, int itemQuantity) {
        Basket currentBasket = basketItemRepository.findFirstByBasketHash(basketHash).get();
        Item currentProduct = itemRepository.findFirstByProductHash(itemHash).get();
        BasketItem currentBasketItem = BasketItem.builder()
                .item(currentProduct)
                .quantity(itemQuantity)
                .build();
        Optional<BasketItem> productInBasketItem = basketAndItemValidator.getProductInBasketItem(currentBasket, currentProduct);
        if (productInBasketItem.isEmpty()) {
            currentBasket.getItemList().add(currentBasketItem);
            basketItemRepository.save(currentBasket);
            return currentBasketItem;
        } else {
            int currentQuantity = productInBasketItem.get().getQuantity();
            productInBasketItem.get().setQuantity(currentQuantity + itemQuantity);
            basketItemRepository.save(currentBasket);
            return productInBasketItem.get();
        }
    }
}