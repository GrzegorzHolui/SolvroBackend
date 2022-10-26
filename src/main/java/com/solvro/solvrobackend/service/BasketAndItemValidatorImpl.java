package com.solvro.solvrobackend.service;

import com.solvro.solvrobackend.Repository.BasketRepository;
import com.solvro.solvrobackend.Repository.ItemRepository;
import com.solvro.solvrobackend.model.Basket;
import com.solvro.solvrobackend.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
class BasketAndItemValidatorImpl implements BasketAndItemValidator {

    BasketRepository basketItemRepository;
    ItemRepository itemRepository;

    private static final int ACCEPTABLE_MESSAGE_SIZE = 1;
    private static final int FIRST_INDEX_IN_LIST = 0;
    private static final int amountToFindItemInBasket = 1;

    @Autowired
    public BasketAndItemValidatorImpl(BasketRepository basketItemRepository, ItemRepository itemRepository) {
        this.basketItemRepository = basketItemRepository;
        this.itemRepository = itemRepository;
    }

    public boolean isBasketAndItemAfterValidationAcceptable(List<BasketAndItemValidatorMessage> validatorMessage) {
        return validatorMessage.size() == ACCEPTABLE_MESSAGE_SIZE &&
                BasketAndItemValidatorMessage.EVERYTHING_IS_FINE.equals(validatorMessage.get(FIRST_INDEX_IN_LIST));
    }

    public List<BasketAndItemValidatorMessage> validate(String basketHash, String itemHash) {
        List<BasketAndItemValidatorMessage> resultValidatorMessages = new ArrayList<>();
        List<BasketAndItemValidatorMessage> validatorMessages = messagesAdder(basketHash, itemHash);
        if (validatorMessages.isEmpty()) {
            resultValidatorMessages.add(BasketAndItemValidatorMessage.EVERYTHING_IS_FINE);
        } else {
            resultValidatorMessages.addAll(validatorMessages);
        }
        return resultValidatorMessages;
    }

    public boolean isItemInBasket(Basket basket, Item itemToCompare) {
        return basket.getItemList().stream()
                .filter(basketItem -> basketItem.getItem().equals(itemToCompare))
                .count() == amountToFindItemInBasket;
    }

    private List<BasketAndItemValidatorMessage> messagesAdder(String basketHash, String itemHash) {
        List<BasketAndItemValidatorMessage> result = new ArrayList<>();
        if (!isItemIdCorrect(itemHash)) {
            result.add(BasketAndItemValidatorMessage.ITEM_NOT_EXIST);
        }
        if (!isBasketIdCorrect(basketHash)) {
            result.add(BasketAndItemValidatorMessage.BASKET_NOT_EXIST);
        }
        return result;
    }


    private boolean isBasketIdCorrect(String basketHash) {
        boolean present = basketItemRepository.findByBasketHash(basketHash).isPresent();
        return present;
    }

    private boolean isItemIdCorrect(String itemHash) {
        boolean present = itemRepository.findByProductHash(itemHash).isPresent();
        return present;
    }
}