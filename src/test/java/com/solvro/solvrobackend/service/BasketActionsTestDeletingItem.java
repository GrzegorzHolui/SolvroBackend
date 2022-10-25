package com.solvro.solvrobackend.service;

import com.solvro.solvrobackend.Repository.BasketItemRepositoryTest;
import com.solvro.solvrobackend.Repository.BasketRepository;
import com.solvro.solvrobackend.Repository.ItemRepository;
import com.solvro.solvrobackend.Repository.ItemRepositoryTest;
import com.solvro.solvrobackend.dto.ServiceResultDto;
import com.solvro.solvrobackend.entity.Basket;
import com.solvro.solvrobackend.entity.BasketItem;
import com.solvro.solvrobackend.entity.Item;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BasketActionsTestDeletingItem {

    @Test
    void shouldDeleteItemInBasket() {
        //given
        Item item = new Item("laptop", BigDecimal.valueOf(10));
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
        ServiceResultDto actualResult = basketActions.deleteItem(basket.getBasketId(), item.getProductId());
        //then
        ServiceResultDto expectedResult =
                new ServiceResultDto(List.of("everything_is_fine"), new BasketItem(item, 1));

        assertThat(actualResult).isEqualTo(expectedResult);

    }

    @Test
    void shouldNotDeleteItemInBasketBecauseItemIsNotInBasket() {
        //given
        Item item = new Item("laptop", BigDecimal.valueOf(10));
        Item itemToFind = new Item("laptop", BigDecimal.valueOf(10));
        BasketItem basketItem = new BasketItem(item, 50);

        BasketItem basketItemToFind = new BasketItem(itemToFind, 1);

        Basket basket = new Basket(new ArrayList<>(List.of(basketItem)));
        ItemRepository itemRepository = new ItemRepositoryTest(new ArrayList<>(List.of(item, itemToFind)));

        BasketRepository basketItemRepository = new BasketItemRepositoryTest(new ArrayList<>(List.of(basket)));

        BasketAndItemValidator basketAndItemValidator =
                new BasketAndItemValidatorImpl(basketItemRepository, itemRepository);
        ValidatorMessageConverter validatorMessageConverter = new ValidatorMessageConverter();

        BasketActions basketActions = new BasketActionsImpl(basketItemRepository,
                itemRepository, basketAndItemValidator, validatorMessageConverter);
        //when
        ServiceResultDto actualResult = basketActions.deleteItem(basket.getBasketId(), itemToFind.getProductId());
        //then
        ServiceResultDto expectedResult =
                new ServiceResultDto(List.of("This product is not in your basket"), null);

        assertThat(actualResult).isEqualTo(expectedResult);
    }
}
