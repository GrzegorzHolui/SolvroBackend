package com.solvro.solvrobackend.service;

import com.solvro.solvrobackend.Repository.BasketRepository;
import com.solvro.solvrobackend.Repository.DiscountCardRepository;
import com.solvro.solvrobackend.Repository.ItemRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    public BasketActions basketActions(BasketRepository basketItemRepository, ItemRepository itemRepository,
                                       DiscountCardRepository discountCardRepository) {
        BasketAndItemValidator basketAndItemValidator = new BasketAndItemValidatorImpl(basketItemRepository, itemRepository);
        ValidatorMessageConverter numberValidatorMessageConverter = new ValidatorMessageConverter();
        BasketItemSaver basketItemSaver = new BasketItemSaver(basketItemRepository, itemRepository, basketAndItemValidator);
        BasketItemDeleter basketItemDeleter = new BasketItemDeleter(basketItemRepository, itemRepository, basketAndItemValidator);
        BasketDiscountSetter basketDiscountSetter = new BasketDiscountSetter(basketItemRepository, itemRepository, basketAndItemValidator, discountCardRepository);
        BasketItemQuantityChanger basketItemQuantityChanger = new BasketItemQuantityChanger(basketItemRepository, itemRepository, basketAndItemValidator);
        BasketDeliveryTypeSetter basketDeliveryTypeSetter = new BasketDeliveryTypeSetter(basketItemRepository, itemRepository, basketAndItemValidator);
        BasketSummaryInfoMaker basketSummaryInfoMaker = new BasketSummaryInfoMaker(basketItemRepository, itemRepository, basketAndItemValidator, discountCardRepository);
        return new BasketActionsImpl(basketItemRepository, itemRepository,
                basketAndItemValidator, numberValidatorMessageConverter,
                basketItemSaver, basketItemDeleter,
                basketItemQuantityChanger, basketDeliveryTypeSetter, basketDiscountSetter, basketSummaryInfoMaker);
    }

    public BasketActions basketActionsTest(BasketRepository basketItemRepository, ItemRepository itemRepository, DiscountCardRepository discountCardRepository) {
        return basketActions(basketItemRepository, itemRepository, discountCardRepository);
    }


}