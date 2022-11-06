package com.solvro.solvrobackend.service;

import com.solvro.solvrobackend.Repository.BasketRepository;
import com.solvro.solvrobackend.Repository.DiscountCardRepository;
import com.solvro.solvrobackend.Repository.ItemRepository;
import com.solvro.solvrobackend.model.Basket;
import com.solvro.solvrobackend.model.SummaryInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class ServiceConfiguration {

    @Bean
    public BasketActions basketActions(BasketRepository basketItemRepository, ItemRepository itemRepository,
                                       DiscountCardRepository discountCardRepository, Basket basket) {
        BasketAndItemValidator basketAndItemValidator = new BasketAndItemValidatorImpl(basketItemRepository, itemRepository);
        ValidatorMessageConverter numberValidatorMessageConverter = new ValidatorMessageConverter();
        BasketItemSaver basketItemSaver = new BasketItemSaver(basket, basketItemRepository, itemRepository, basketAndItemValidator);
        BasketItemDeleter basketItemDeleter = new BasketItemDeleter(basket, basketItemRepository, itemRepository, basketAndItemValidator);
        BasketDiscountSetter basketDiscountSetter = new BasketDiscountSetter(basket, basketItemRepository, itemRepository, basketAndItemValidator, discountCardRepository);
        BasketItemQuantityChanger basketItemQuantityChanger = new BasketItemQuantityChanger(basket, basketItemRepository, itemRepository, basketAndItemValidator);
        BasketDeliveryTypeSetter basketDeliveryTypeSetter = new BasketDeliveryTypeSetter(basket, basketItemRepository, itemRepository, basketAndItemValidator);
        BasketSummaryInfoMaker basketSummaryInfoMaker = new BasketSummaryInfoMaker(basket, basketItemRepository, itemRepository, basketAndItemValidator, discountCardRepository);
        return new BasketActionsImpl(basket, basketAndItemValidator, numberValidatorMessageConverter,
                basketItemSaver, basketItemDeleter,
                basketItemQuantityChanger, basketDeliveryTypeSetter, basketDiscountSetter, basketSummaryInfoMaker);
    }

    public BasketActions basketActionsTest(BasketRepository basketItemRepository, ItemRepository itemRepository, DiscountCardRepository discountCardRepository, Basket basket) {
        return basketActions(basketItemRepository, itemRepository, discountCardRepository, basket);
    }


}