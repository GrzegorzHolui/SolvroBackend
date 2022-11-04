package com.solvro.solvrobackend.service;

import com.solvro.solvrobackend.Repository.BasketRepository;
import com.solvro.solvrobackend.Repository.ItemRepository;
import com.solvro.solvrobackend.model.Basket;
import com.solvro.solvrobackend.model.DeliveryType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
class BasketDeliveryTypeSetter {
    BasketRepository basketItemRepository;
    ItemRepository itemRepository;
    BasketAndItemValidator basketAndItemValidator;

    public DeliveryType setBasketDeliveryType(String basketHash, DeliveryType deliveryType) {
        Basket currentBasket = basketItemRepository.findFirstByBasketHash(basketHash).get();
        currentBasket.getSummaryInfo().setDeliveryType(deliveryType);
        basketItemRepository.save(currentBasket);
        return deliveryType;
    }
}
