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

    BasketItem changeQuantity(String basketHash, String itemHash, int newQuantity, List<String> validatorMessage) {
        Basket currentBasket = basketItemRepository.findByBasketHash(basketHash).get();
        Item productToChangeQuantity = itemRepository.findByProductHash(itemHash).get();
        if (basketAndItemValidator.getProductInBasketItem(currentBasket, productToChangeQuantity).isEmpty()) {
            addMessageToValidatorMessage(validatorMessage, "This product is not in your basket");
//            System.out.println(basketItemRepository.findByBasketHash(basketHash));
            return null;
        }
        for (BasketItem item : currentBasket.getItemList()) {
            if (item.getItem().equals(productToChangeQuantity)) {
                item.setQuantity(newQuantity);
                basketItemRepository.save(currentBasket);
//                System.out.println(basketItemRepository.findByBasketHash(basketHash));
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
