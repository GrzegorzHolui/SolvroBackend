package com.solvro.solvrobackend.service;

import com.solvro.solvrobackend.Repository.BasketRepository;
import com.solvro.solvrobackend.Repository.ItemRepository;
import com.solvro.solvrobackend.model.Basket;
import com.solvro.solvrobackend.model.BasketItem;
import com.solvro.solvrobackend.model.Item;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
class BasketAndItemValidatorImpl implements BasketAndItemValidator {
    BasketRepository basketItemRepository;
    ItemRepository itemRepository;
    private static final int ACCEPTABLE_MESSAGE_SIZE = 1;
    private static final int FIRST_INDEX_IN_LIST = 0;

    private static final int amountToFindItemInBasket = 1;

    public BasketAndItemValidatorImpl(BasketRepository basketItemRepository, ItemRepository itemRepository) {
        this.basketItemRepository = basketItemRepository;
        this.itemRepository = itemRepository;
    }

    public boolean isBasketAndItemAfterValidationAcceptable(List<BasketAndItemValidatorMessage> validatorMessage) {
        return validatorMessage.size() == ACCEPTABLE_MESSAGE_SIZE &&
                BasketAndItemValidatorMessage.EVERYTHING_IS_FINE.equals(validatorMessage.get(FIRST_INDEX_IN_LIST));
    }

    @Override
    public List<BasketAndItemValidatorMessage> validateBasket(String basketHash) {
        List<BasketAndItemValidatorMessage> resultValidatorMessages = new ArrayList<>();
        if (basketItemRepository.findFirstByBasketHash(basketHash).isPresent()) {
            resultValidatorMessages.add(BasketAndItemValidatorMessage.EVERYTHING_IS_FINE);
        } else {
            resultValidatorMessages.add(BasketAndItemValidatorMessage.BASKET_NOT_EXIST);
        }
        return resultValidatorMessages;
    }

    public List<BasketAndItemValidatorMessage> validateBasketAndItem(String basketHash, String itemHash) {
        List<BasketAndItemValidatorMessage> resultValidatorMessages = new ArrayList<>();
        List<BasketAndItemValidatorMessage> validatorMessages = messagesAdder(basketHash, itemHash);
        if (validatorMessages.isEmpty()) {
            resultValidatorMessages.add(BasketAndItemValidatorMessage.EVERYTHING_IS_FINE);
        } else {
            resultValidatorMessages.addAll(validatorMessages);
        }
        return resultValidatorMessages;
    }

    public Optional<BasketItem> findFirstProductInBasketItem(Basket basket, Item item) {
        return basket.getItemList().stream()
                .filter(basketItem -> basketItem.getItem().equals(item))
                .findFirst();
    }

    private List<BasketAndItemValidatorMessage> messagesAdder(String basketHash, String itemHash) {
        List<BasketAndItemValidatorMessage> result = new ArrayList<>();
        if (!isItemHashCorrect(itemHash)) {
            result.add(BasketAndItemValidatorMessage.ITEM_NOT_EXIST);
        }
        if (!isBasketHashCorrect(basketHash)) {
            result.add(BasketAndItemValidatorMessage.BASKET_NOT_EXIST);
        }
        return result;
    }


    private boolean isBasketHashCorrect(String basketHash) {
        boolean present = basketItemRepository.findFirstByBasketHash(basketHash).isPresent();
        return present;
    }

    private boolean isItemHashCorrect(String itemHash) {
        boolean present = itemRepository.findFirstByProductHash(itemHash).isPresent();
        return present;
    }
}