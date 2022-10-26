package com.solvro.solvrobackend.service;

import com.solvro.solvrobackend.Repository.BasketRepositoryTest;
import com.solvro.solvrobackend.Repository.BasketRepository;
import com.solvro.solvrobackend.Repository.ItemRepository;
import com.solvro.solvrobackend.Repository.ItemRepositoryTest;
import com.solvro.solvrobackend.dto.ServiceResultDto;
import com.solvro.solvrobackend.model.Basket;
import com.solvro.solvrobackend.model.BasketItem;
import com.solvro.solvrobackend.model.Item;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BasketActionsTestDeletingItem implements SampleRepository {

    @Test
    void shouldDeleteItemInBasket() {
        //given
        Item item = new Item("laptop", BigDecimal.valueOf(10), "hash1");
        BasketItem basketItem = new BasketItem(item, 1);
        Basket basket = new Basket(new ArrayList<>(List.of(basketItem)), "basketHash");

        ItemRepository itemRepository = sampleItemRepository(item);
        BasketRepository basketItemRepository = sampleBasketRepository(basket);

        BasketActions basketActions = new ServiceConfiguration().basketActionsTest(basketItemRepository, itemRepository);
        //when
        ServiceResultDto actualResult = basketActions.deleteItem(basket.getBasketHash(), item.getProductHash());
        //then
        ServiceResultDto expectedResult =
                new ServiceResultDto(List.of("everything_is_fine"), new BasketItem(item, 1));

        assertThat(actualResult).isEqualTo(expectedResult);

    }

    @Test
    void shouldNotDeleteItemInBasketBecauseItemIsNotInBasket() {
        //given
        Item item = new Item("laptop", BigDecimal.valueOf(10), "hash1");
        Item itemToFind = new Item("laptop", BigDecimal.valueOf(10), "hash2");
        BasketItem basketItem = new BasketItem(item, 50);

        Basket basket = new Basket(new ArrayList<>(List.of(basketItem)), "basketHash");
        ItemRepository itemRepository = new ItemRepositoryTest(new ArrayList<>(List.of(item, itemToFind)));
        BasketRepository basketItemRepository = new BasketRepositoryTest(new ArrayList<>(List.of(basket)));
        BasketActions basketActions = new ServiceConfiguration().basketActionsTest(basketItemRepository, itemRepository);
        //when
        ServiceResultDto actualResult = basketActions.deleteItem(basket.getBasketHash(), itemToFind.getProductHash());
        //then
        ServiceResultDto expectedResult =
                new ServiceResultDto(List.of("This product is not in your basket"), null);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void shouldNotDeleteItemInBasketBecauseThereIsNoItem() {
        //given
        Item item = new Item("laptop", BigDecimal.valueOf(10), "hash1");
        Item itemToFind = new Item("laptop", BigDecimal.valueOf(10), "hash2");
        BasketItem basketItem = new BasketItem(item, 50);

        Basket basket = new Basket(new ArrayList<>(List.of(basketItem)), "basketHash");
        ItemRepository itemRepository = new ItemRepositoryTest(new ArrayList<>(List.of(item)));
        BasketRepository basketItemRepository = new BasketRepositoryTest(new ArrayList<>(List.of(basket)));
        BasketActions basketActions = new ServiceConfiguration().basketActionsTest(basketItemRepository, itemRepository);
        //when
        ServiceResultDto actualResult = basketActions.deleteItem(basket.getBasketHash(), itemToFind.getProductHash());
        //then
        ServiceResultDto expectedResult =
                new ServiceResultDto(List.of("item_doesn't_exist"), null);

        assertThat(actualResult).isEqualTo(expectedResult);
    }
}
