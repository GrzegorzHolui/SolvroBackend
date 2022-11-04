package service;

import com.solvro.solvrobackend.Repository.BasketRepository;
import com.solvro.solvrobackend.Repository.DiscountCardRepository;
import com.solvro.solvrobackend.Repository.DiscountCardRepositoryTest;
import com.solvro.solvrobackend.Repository.ItemRepository;
import com.solvro.solvrobackend.dto.ServiceChangeQuantityResultDto;
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

import static org.assertj.core.api.Assertions.assertThat;

class BasketActionsChangingQuantityTest implements SampleRepository {

    @Test
    void shouldChangeItemQuantity() {
        //given
        Item item = new Item("laptop", BigDecimal.valueOf(10), "hash1");
        BasketItem basketItem = new BasketItem(item, 1);
        Basket basket = new Basket(new ArrayList<>(List.of(basketItem)), "baskethash");

        ItemRepository itemRepository = sampleItemRepository(item);
        BasketRepository basketItemRepository = sampleBasketRepository(basket);
        DiscountCardRepository discountCardRepository = new DiscountCardRepositoryTest();

        BasketActions basketActions = new ServiceConfiguration().basketActionsTest(basketItemRepository, itemRepository, discountCardRepository);
        //when
        ServiceChangeQuantityResultDto actualResult = basketActions.changeAmountOfProduct
                (basket.getBasketHash(), item.getProductHash(), 10);
        //then
        int expectedQuantity = 10;
        List<String> expectedMessage = List.of("everything_is_fine", "item has another quantity");
        assertThat((actualResult.message())).isEqualTo(expectedMessage);
        assertThat((actualResult.quantity())).isEqualTo(expectedQuantity);
    }

    @Test
    void shouldNotChangeItemQuantityBecauseIsBadHashBasket() {
        //given
        Item item = new Item("laptop", BigDecimal.valueOf(10), "hash1");
        BasketItem basketItem = new BasketItem(item, 1);
        Basket basket = new Basket(new ArrayList<>(List.of(basketItem)), "basketHash");

        ItemRepository itemRepository = sampleItemRepository(item);
        BasketRepository basketItemRepository = sampleBasketRepository(basket);
        DiscountCardRepository discountCardRepository = new DiscountCardRepositoryTest();

        BasketActions basketActions = new ServiceConfiguration().basketActionsTest(basketItemRepository, itemRepository, discountCardRepository);
        //when
        ServiceChangeQuantityResultDto actualResult = basketActions.changeAmountOfProduct
                ("falseBasketHash", item.getProductHash(), 10);
        //then
        ServiceChangeQuantityResultDto expected = new ServiceChangeQuantityResultDto(List.of("basket_doesn't_exist"), null);
        assertThat(actualResult).isEqualTo(expected);
    }
}