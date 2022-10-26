package com.solvro.solvrobackend.service;

import com.solvro.solvrobackend.Repository.BasketRepositoryTest;
import com.solvro.solvrobackend.Repository.BasketRepository;
import com.solvro.solvrobackend.Repository.ItemRepository;
import com.solvro.solvrobackend.Repository.ItemRepositoryTest;
import com.solvro.solvrobackend.model.Basket;
import com.solvro.solvrobackend.model.BasketItem;
import com.solvro.solvrobackend.model.Item;
import com.solvro.solvrobackend.dto.ServiceResultDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


class BasketActionsTestAddingItem implements SampleRepository {


    @Test
    void shouldReturnThatEveryThingIsAlrightAfterAddItemIfIsBasketItemInDataBase() {
        //given
        Item item = new Item("laptop", BigDecimal.valueOf(10));
        BasketItem basketItem = new BasketItem(item, 1);
        Basket basket = new Basket(new ArrayList<>(List.of(basketItem)));

        ItemRepository itemRepository = sampleItemRepository(item);
        BasketRepository basketItemRepository = sampleBasketRepository(basket);

        BasketActions basketActions = new ServiceConfiguration().basketActionsTest(basketItemRepository, itemRepository);
        //when
        ServiceResultDto actualResult = basketActions.addItem(basket.getBasketId(), item.getProductId(), 10);
        //then
        ServiceResultDto expectedResult =
                new ServiceResultDto(List.of("everything_is_fine"), new BasketItem(item, 11));

        assertThat(actualResult).isEqualTo(expectedResult);

    }

    @Test
    void shouldReturnThatEveryThingIsAlrightAfterAddItemIfIsBasketItemNotInDataBase() {
        Item item = new Item("laptop", BigDecimal.valueOf(10));
        BasketItem basketItem = new BasketItem(item, 1);
        Basket basket = new Basket(new ArrayList<>(List.of(basketItem)));

        ItemRepository itemRepository = sampleItemRepository(item);
        BasketRepository basketItemRepository = sampleBasketRepository(basket);

        BasketActions basketActions = new ServiceConfiguration().basketActionsTest(basketItemRepository, itemRepository);
        //when
        ServiceResultDto actualResult = basketActions.addItem(basket.getBasketId(), item.getProductId(), 10);
        //then
        ServiceResultDto expectedResult =
                new ServiceResultDto(List.of("everything_is_fine"), new BasketItem(item, 1));

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnThatBasketAndItemDoesntExist() {
        Item item = new Item("laptop", BigDecimal.valueOf(10));
        BasketItem basketItem = new BasketItem(item, 1);
        Basket basket = new Basket(new ArrayList<>(List.of(basketItem)));

        ItemRepository itemRepository = sampleItemRepository(item);
        BasketRepository basketItemRepository = sampleBasketRepository(basket);

        BasketActions basketActions = new ServiceConfiguration().basketActionsTest(basketItemRepository, itemRepository);
        //when
        ServiceResultDto actualResult = basketActions.addItem(UUID.randomUUID(), UUID.randomUUID(), 10);
        //then
        ServiceResultDto expectedResult =
                new ServiceResultDto(List.of("item_doesn't_exist", "basket_doesn't_exist"), null);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

}