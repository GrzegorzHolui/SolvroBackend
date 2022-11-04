package service;

import com.solvro.solvrobackend.Repository.BasketRepository;
import com.solvro.solvrobackend.Repository.DiscountCardRepository;
import com.solvro.solvrobackend.Repository.DiscountCardRepositoryTest;
import com.solvro.solvrobackend.Repository.ItemRepository;
import com.solvro.solvrobackend.dto.ServiceCrudResultDto;
import com.solvro.solvrobackend.model.Basket;
import com.solvro.solvrobackend.model.BasketItem;
import com.solvro.solvrobackend.model.Item;
import com.solvro.solvrobackend.service.BasketActions;
import com.solvro.solvrobackend.service.ServiceConfiguration;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


class BasketActionsAddingItemTest implements SampleRepository {
    @Test
    void shouldReturnThatEveryThingIsAlrightAfterAddItemIfIsBasketItemIsInDataBase() {
        //given
        Item item = new Item("laptop", BigDecimal.valueOf(10), "hash1");
        BasketItem basketItem = new BasketItem(item, 1);
        Basket basket = new Basket(new ArrayList<>(List.of(basketItem)), UUID.randomUUID().toString());

        ItemRepository itemRepository = sampleItemRepository(item);
        BasketRepository basketItemRepository = sampleBasketRepository(basket);
        DiscountCardRepository discountCardRepository = new DiscountCardRepositoryTest();

        BasketActions basketActions = new ServiceConfiguration().basketActionsTest(basketItemRepository, itemRepository, discountCardRepository);
        //when
        ServiceCrudResultDto actualResult = basketActions.addItem(basket.getBasketHash(), item.getProductHash(), 10);
        //then
        ServiceCrudResultDto expectedResult =
                new ServiceCrudResultDto(List.of("everything_is_fine", "item has been added"), new BasketItem(item, 11));

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnThatEveryThingIsAlrightAfterAddItemIfIsBasketItemNotInDataBase() {
        //given
        Item item = new Item("laptop", BigDecimal.valueOf(10), "hash1");
        BasketItem basketItem = new BasketItem(item, 1);
        Basket basket = new Basket(new ArrayList<>(List.of(basketItem)), UUID.randomUUID().toString());

        ItemRepository itemRepository = sampleItemRepository(item);
        BasketRepository basketItemRepository = sampleBasketRepository(basket);
        DiscountCardRepository discountCardRepository = new DiscountCardRepositoryTest();
        BasketActions basketActions = new ServiceConfiguration().basketActionsTest(basketItemRepository, itemRepository, discountCardRepository);
        //when
        ServiceCrudResultDto actualResult = basketActions.addItem(basket.getBasketHash(), item.getProductHash(), 10);
        //then
        ServiceCrudResultDto expectedResult =
                new ServiceCrudResultDto(List.of("everything_is_fine", "item has been added"), new BasketItem(item, 1));

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnThatBasketAndItemDoesntExist() {
        //given
        Item item = new Item("laptop", BigDecimal.valueOf(10), "hash1");
        BasketItem basketItem = new BasketItem(item, 1);
        Basket basket = new Basket(new ArrayList<>(List.of(basketItem)), UUID.randomUUID().toString());

        ItemRepository itemRepository = sampleItemRepository(item);
        BasketRepository basketItemRepository = sampleBasketRepository(basket);
        DiscountCardRepository discountCardRepository = new DiscountCardRepositoryTest();

        BasketActions basketActions = new ServiceConfiguration().basketActionsTest(basketItemRepository, itemRepository, discountCardRepository);
        //when
        ServiceCrudResultDto actualResult = basketActions.addItem(UUID.randomUUID().toString(), UUID.randomUUID().toString(), 10);
        //then
        ServiceCrudResultDto expectedResult =
                new ServiceCrudResultDto(List.of("item_doesn't_exist", "basket_doesn't_exist"), null);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

}