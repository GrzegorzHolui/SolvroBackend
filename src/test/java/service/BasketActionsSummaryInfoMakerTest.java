package service;

import com.solvro.solvrobackend.Repository.BasketRepository;
import com.solvro.solvrobackend.Repository.DiscountCardRepository;
import com.solvro.solvrobackend.Repository.DiscountCardRepositoryTest;
import com.solvro.solvrobackend.Repository.ItemRepository;
import com.solvro.solvrobackend.dto.ServiceResultDto;
import com.solvro.solvrobackend.model.Basket;
import com.solvro.solvrobackend.model.BasketItem;
import com.solvro.solvrobackend.model.DeliveryType;
import com.solvro.solvrobackend.model.DiscountCard;
import com.solvro.solvrobackend.model.Item;
import com.solvro.solvrobackend.model.SummaryInfo;
import com.solvro.solvrobackend.model.TypeOfDiscount;
import com.solvro.solvrobackend.service.BasketActions;
import com.solvro.solvrobackend.service.ServiceConfiguration;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class BasketActionsSummaryInfoMakerTest implements SampleRepository {
    @Test
    void shouldReturnTheCorrectSumOfAllProductsWithConstantDiscount() {
        Item item = new Item("laptop", BigDecimal.valueOf(10), "hash1");
        BasketItem basketItem = new BasketItem(item, 1);
        Basket basket = new Basket(new ArrayList<>(List.of(basketItem)), UUID.randomUUID().toString());
        ItemRepository itemRepository = sampleItemRepository(item);
        BasketRepository basketItemRepository = sampleBasketRepository(basket);

        basket.getSummaryInfo().setDeliveryType(DeliveryType.COURIER_DELIVERY_INPOST);
        DiscountCard discountCard = new DiscountCard("cardHash", "laptop", TypeOfDiscount.CONSTANT, BigDecimal.ONE);
        DiscountCardRepository discountCardRepository = new DiscountCardRepositoryTest(List.of(discountCard));
        basket.getSummaryInfo().setUsedCard(List.of(discountCard));
        BasketActions basketActions = new ServiceConfiguration().basketActionsTest(basketItemRepository, itemRepository, discountCardRepository);
        //when
        ServiceResultDto actualResult = basketActions
                .getInformationAboutBasket(basket.getBasketHash());
        //then
        ServiceResultDto expectedResult =
                new ServiceResultDto(List.of("everything_is_fine"), new SummaryInfo(BigDecimal.valueOf(10), DeliveryType.COURIER_DELIVERY_INPOST, List.of(discountCard), BigDecimal.valueOf(29)));

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnTheCorrectSumOfAllProductsWithPercentDiscount() {
        Item item = new Item("laptop", BigDecimal.valueOf(10), "hash1");
        BasketItem basketItem = new BasketItem(item, 1);
        Basket basket = new Basket(new ArrayList<>(List.of(basketItem)), UUID.randomUUID().toString());
        ItemRepository itemRepository = sampleItemRepository(item);
        BasketRepository basketItemRepository = sampleBasketRepository(basket);
        basket.getSummaryInfo().setDeliveryType(DeliveryType.COURIER_DELIVERY_INPOST);
        DiscountCard discountCard = new DiscountCard("cardHash", "laptop", TypeOfDiscount.PERCENT, BigDecimal.valueOf(0.1));
        DiscountCardRepository discountCardRepository = new DiscountCardRepositoryTest(List.of(discountCard));
        basket.getSummaryInfo().setUsedCard(List.of(discountCard));
        BasketActions basketActions = new ServiceConfiguration().basketActionsTest(basketItemRepository, itemRepository, discountCardRepository);
        //when
        ServiceResultDto actualResult = basketActions
                .getInformationAboutBasket(basket.getBasketHash());
        //then
        ServiceResultDto expectedResult =
                new ServiceResultDto(List.of("everything_is_fine"), new SummaryInfo(BigDecimal.valueOf(10), DeliveryType.COURIER_DELIVERY_INPOST, List.of(discountCard), BigDecimal.valueOf(29.0)));

        assertThat(actualResult).isEqualTo(expectedResult);
    }


    @Test
    void shouldReturnTheCorrectSumOfAllProductsWithPercentDiscountButWithNullDeliveryType() {
        Item item = new Item("laptop", BigDecimal.valueOf(10), "hash1");
        BasketItem basketItem = new BasketItem(item, 1);
        Basket basket = new Basket(new ArrayList<>(List.of(basketItem)), UUID.randomUUID().toString());
        ItemRepository itemRepository = sampleItemRepository(item);
        BasketRepository basketItemRepository = sampleBasketRepository(basket);
        DiscountCard discountCard = new DiscountCard("cardHash", "laptop", TypeOfDiscount.PERCENT, BigDecimal.valueOf(0.1));
        DiscountCardRepository discountCardRepository = new DiscountCardRepositoryTest(List.of(discountCard));
        basket.getSummaryInfo().setUsedCard(List.of(discountCard));
        BasketActions basketActions = new ServiceConfiguration().basketActionsTest(basketItemRepository, itemRepository, discountCardRepository);
        //when
        ServiceResultDto actualResult = basketActions
                .getInformationAboutBasket(basket.getBasketHash());
        //then
        ServiceResultDto expectedResult =
                new ServiceResultDto(List.of("everything_is_fine"), new SummaryInfo(BigDecimal.valueOf(10), null, List.of(discountCard), BigDecimal.valueOf(9.0)));
        assertThat(actualResult).isEqualTo(expectedResult);
    }

}