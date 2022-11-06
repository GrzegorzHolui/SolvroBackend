package com.solvro.solvrobackend.service;

import com.solvro.solvrobackend.Repository.BasketRepository;
import com.solvro.solvrobackend.Repository.DiscountCardRepository;
import com.solvro.solvrobackend.Repository.ItemRepository;
import com.solvro.solvrobackend.model.Basket;
import com.solvro.solvrobackend.model.DiscountCard;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
class BasketDiscountSetter {
    Basket basket;
    BasketRepository basketItemRepository;
    ItemRepository itemRepository;
    BasketAndItemValidator basketAndItemValidator;
    DiscountCardRepository discountCardRepository;

    public DiscountCard setBasketDiscount(String discountCardHash) {
        Optional<DiscountCard> byCardHash = discountCardRepository.findFirstByCardHash(discountCardHash);
        if (byCardHash.isPresent()) {
            basket.getSummaryInfo().getUsedCard().add(byCardHash.get());
            basketItemRepository.save(basket);
            return byCardHash.get();
        }
        return null;
    }
}
