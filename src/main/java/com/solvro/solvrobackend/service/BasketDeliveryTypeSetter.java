package com.solvro.solvrobackend.service;

import com.solvro.solvrobackend.Repository.BasketRepository;
import com.solvro.solvrobackend.Repository.ItemRepository;
import com.solvro.solvrobackend.model.Basket;
import com.solvro.solvrobackend.model.DeliveryType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
class BasketDeliveryTypeSetter {

    Basket basket;
    BasketRepository basketItemRepository;
    ItemRepository itemRepository;
    BasketAndItemValidator basketAndItemValidator;

    public DeliveryType setBasketDeliveryType(DeliveryType deliveryType) {
        basket.getSummaryInfo().setDeliveryType(deliveryType);
        basketItemRepository.save(basket);
        return deliveryType;
    }
}
