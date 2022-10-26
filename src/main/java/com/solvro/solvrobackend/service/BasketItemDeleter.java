package com.solvro.solvrobackend.service;

import com.solvro.solvrobackend.Repository.BasketRepository;
import com.solvro.solvrobackend.Repository.ItemRepository;
import com.solvro.solvrobackend.dto.ServiceResultDto;
import com.solvro.solvrobackend.model.Basket;
import com.solvro.solvrobackend.model.BasketItem;
import com.solvro.solvrobackend.model.Item;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
class BasketItemDeleter {
    BasketRepository basketItemRepository;
    ItemRepository itemRepository;
    BasketAndItemValidator basketAndItemValidator;


    BasketItem deleteBasketItem(String basketHash, String itemHash, List<String> validatorMessage) {
        Basket currentBasket = basketItemRepository.findByBasketHash(basketHash).get();
        Item productToRemove = itemRepository.findByProductHash(itemHash).get();
        if (!basketAndItemValidator.isItemInBasket(currentBasket, productToRemove)) {
            addMessageToValidatorMessage(validatorMessage, "This product is not in your basket");
//                System.out.println(basketItemRepository.findByHash(basketHash));
            return null;
        }
        for (BasketItem item : currentBasket.getItemList()) {
            if (item.getItem().equals(productToRemove)) {
                currentBasket.getItemList().remove(item);
                basketItemRepository.save(currentBasket);
//                    System.out.println(basketItemRepository.findByHash(basketHash));
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
