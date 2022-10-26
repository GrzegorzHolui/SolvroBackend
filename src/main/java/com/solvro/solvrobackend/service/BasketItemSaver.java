package com.solvro.solvrobackend.service;

import com.solvro.solvrobackend.Repository.BasketRepository;
import com.solvro.solvrobackend.Repository.ItemRepository;
import com.solvro.solvrobackend.model.Basket;
import com.solvro.solvrobackend.model.BasketItem;
import com.solvro.solvrobackend.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
class BasketItemSaver {
    BasketRepository basketItemRepository;
    ItemRepository itemRepository;

    @Autowired
    public BasketItemSaver(BasketRepository basketItemRepository, ItemRepository itemRepository) {
        this.basketItemRepository = basketItemRepository;
        this.itemRepository = itemRepository;
    }

    BasketItem saveBasketItem(String basketHash, String itemHash, int itemQuantity) {
        Basket currentBasket = basketItemRepository.findByBasketHash(basketHash).get();
        Item currentProduct = itemRepository.findByProductHash(itemHash).get();
        BasketItem currentBasketItem = BasketItem.builder()
                .item(currentProduct)
                .quantity(itemQuantity)
                .build();
        Optional<BasketItem> productInBasketItem = currentBasket.isProductInBasketItem(currentBasketItem);
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
