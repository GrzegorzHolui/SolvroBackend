package com.solvro.solvrobackend.service;

import com.solvro.solvrobackend.Repository.BasketItemRepositoryTest;
import com.solvro.solvrobackend.Repository.BasketRepository;
import com.solvro.solvrobackend.Repository.ItemRepository;
import com.solvro.solvrobackend.Repository.ItemRepositoryTest;
import com.solvro.solvrobackend.entity.Basket;
import com.solvro.solvrobackend.entity.BasketItem;
import com.solvro.solvrobackend.entity.Item;
import com.solvro.solvrobackend.dto.ServiceResultDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


class BasketActionsTestAddingItem {


    @Test
    void shouldReturnThatEveryThingIsAlrightAfterAddItemIfIsBasketItemInDataBase() {
        //given
        Item item = new Item("laptop", BigDecimal.valueOf(10));
//        Todo make builder to item
//        BasketItem basketItem = new BasketItem(item, 50);

        BasketItem basketItem = new BasketItem(item, 1);

        Basket basket = new Basket(new ArrayList<>(List.of(basketItem)));
        ItemRepository itemRepository = new ItemRepositoryTest(new ArrayList<>(List.of(item)));

        BasketRepository basketItemRepository = new BasketItemRepositoryTest(new ArrayList<>(List.of(basket)));

        BasketAndItemValidator basketAndItemValidator =
                new BasketAndItemValidatorImpl(basketItemRepository, itemRepository);
        ValidatorMessageConverter validatorMessageConverter = new ValidatorMessageConverter();

        BasketActions basketActions = new BasketActionsImpl(basketItemRepository,
                itemRepository, basketAndItemValidator, validatorMessageConverter);
        //when
        ServiceResultDto actualResult = basketActions.addItem(basket.getBasketId(), item.getProductId(), 10);
        //then
        ServiceResultDto expectedResult =
                new ServiceResultDto(List.of("everything_is_fine"), new BasketItem(item, 11));

        assertThat(actualResult).isEqualTo(expectedResult);

    }

    @Test
    void shouldReturnThatEveryThingIsAlrightAfterAddItemIfIsBasketItemNotInDataBase() {
        //given
        Item item = new Item("laptop", BigDecimal.valueOf(10));
//        BasketItem basketItem = new BasketItem(item, 50);

        BasketItem basketItem = new BasketItem(item, 1);

        Basket basket = new Basket(new ArrayList<>());
        ItemRepository itemRepository = new ItemRepositoryTest(new ArrayList<>(List.of(item)));

        BasketRepository basketItemRepository = new BasketItemRepositoryTest(new ArrayList<>(List.of(basket)));

        BasketAndItemValidator basketAndItemValidator =
                new BasketAndItemValidatorImpl(basketItemRepository, itemRepository);
        ValidatorMessageConverter validatorMessageConverter = new ValidatorMessageConverter();

        BasketActions basketActions = new BasketActionsImpl(basketItemRepository,
                itemRepository, basketAndItemValidator, validatorMessageConverter);
        //when
        ServiceResultDto actualResult = basketActions.addItem(basket.getBasketId(), item.getProductId(), 10);
        //then
        ServiceResultDto expectedResult =
                new ServiceResultDto(List.of("everything_is_fine"), new BasketItem(item, 1));

        assertThat(actualResult).isEqualTo(expectedResult);

    }

    @Test
    void shouldReturnThatBasketAndItemDoesntExist() {
        //given
        Item item = new Item("laptop", BigDecimal.valueOf(10));

        Basket basket = new Basket(new ArrayList<>());
        ItemRepository itemRepository = new ItemRepositoryTest(new ArrayList<>(List.of(item)));

        BasketRepository basketItemRepository = new BasketItemRepositoryTest(new ArrayList<>(List.of(basket)));

        BasketAndItemValidator basketAndItemValidator =
                new BasketAndItemValidatorImpl(basketItemRepository, itemRepository);
        ValidatorMessageConverter validatorMessageConverter = new ValidatorMessageConverter();

        BasketActions basketActions = new BasketActionsImpl(basketItemRepository,
                itemRepository, basketAndItemValidator, validatorMessageConverter);
        //when
        ServiceResultDto actualResult = basketActions.addItem(UUID.randomUUID(), UUID.randomUUID(), 10);
        //then
        ServiceResultDto expectedResult =
                new ServiceResultDto(List.of("item_doesn't_exist", "basket_doesn't_exist"), null);

        assertThat(actualResult).isEqualTo(expectedResult);

    }

}