package com.solvro.solvrobackend.service;

import com.solvro.solvrobackend.Repository.BasketRepository;
import com.solvro.solvrobackend.Repository.ItemRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    public BasketActions basketActions(BasketRepository basketItemRepository, ItemRepository itemRepository) {
        BasketAndItemValidator basketAndItemValidator = new BasketAndItemValidatorImpl(basketItemRepository, itemRepository);
        ValidatorMessageConverter numberValidatorMessageConverter = new ValidatorMessageConverter();
        BasketItemSaver basketItemSaver = new BasketItemSaver(basketItemRepository, itemRepository);
        BasketItemDeleter basketItemDeleter = new BasketItemDeleter(basketItemRepository, itemRepository, basketAndItemValidator);
        BasketItemQuantityChanger basketItemQuantityChanger = new BasketItemQuantityChanger(basketItemRepository, itemRepository, basketAndItemValidator);
        return new BasketActionsImpl(basketItemRepository, itemRepository,
                basketAndItemValidator, numberValidatorMessageConverter, basketItemSaver, basketItemDeleter, basketItemQuantityChanger);
    }

    public BasketActions basketActionsTest(BasketRepository basketItemRepository, ItemRepository itemRepository) {
        return basketActions(basketItemRepository, itemRepository);
    }
}