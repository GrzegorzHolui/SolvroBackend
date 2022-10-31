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
    BasketRepository basketItemRepository;
    ItemRepository itemRepository;
    BasketAndItemValidator basketAndItemValidator;
    DiscountCardRepository discountCardRepository;

    public DiscountCard setBasketDiscount(String basketHash, String discountCardHash) {
        Basket currentBasket = basketItemRepository.findFirstByBasketHash(basketHash).get();
        Optional<DiscountCard> byCardHash = discountCardRepository.findFirstByCardHash(discountCardHash);
        if (byCardHash.isPresent()) {
            currentBasket.getSummaryInfo().getUsedCard().add(byCardHash.get());
            return byCardHash.get();
        }
        return null;
    }
}
