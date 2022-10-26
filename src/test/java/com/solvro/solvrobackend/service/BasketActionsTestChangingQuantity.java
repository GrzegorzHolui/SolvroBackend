package com.solvro.solvrobackend.service;

import com.solvro.solvrobackend.Repository.BasketRepository;
import com.solvro.solvrobackend.Repository.ItemRepository;
import com.solvro.solvrobackend.dto.ServiceResultDto;
import com.solvro.solvrobackend.model.Basket;
import com.solvro.solvrobackend.model.BasketItem;
import com.solvro.solvrobackend.model.Item;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class BasketActionsTestChangingQuantity implements SampleRepository {

    @Test
    void shouldChangeItemQuantity() {
        //given
        Item item = new Item("laptop", BigDecimal.valueOf(10), "hash1");
        BasketItem basketItem = new BasketItem(item, 1);
        Basket basket = new Basket(new ArrayList<>(List.of(basketItem)), UUID.randomUUID().toString());

        ItemRepository itemRepository = sampleItemRepository(item);
        BasketRepository basketItemRepository = sampleBasketRepository(basket);

        BasketActions basketActions = new ServiceConfiguration().basketActionsTest(basketItemRepository, itemRepository);
        //when
        ServiceResultDto actualResult = basketActions.changeAmountOfProduct
                (basket.getBasketHash(), item.getProductHash(), 10);
        //then
        int expectedQuantity = 10;
        assertThat(actualResult.basketItem().getQuantity()).isEqualTo(expectedQuantity);
    }
}
