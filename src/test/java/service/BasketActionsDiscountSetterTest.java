package service;

import com.solvro.solvrobackend.Repository.BasketRepository;
import com.solvro.solvrobackend.Repository.DiscountCardRepository;
import com.solvro.solvrobackend.Repository.DiscountCardRepositoryTest;
import com.solvro.solvrobackend.Repository.ItemRepository;
import com.solvro.solvrobackend.dto.ServiceDiscountCardResultDto;
import com.solvro.solvrobackend.model.Basket;
import com.solvro.solvrobackend.model.BasketItem;
import com.solvro.solvrobackend.model.DiscountCard;
import com.solvro.solvrobackend.model.Item;
import com.solvro.solvrobackend.model.TypeOfDiscount;
import com.solvro.solvrobackend.service.BasketActions;
import com.solvro.solvrobackend.service.ServiceConfiguration;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class BasketActionsDiscountSetterTest implements SampleRepository {

    @Test
    void setBasketDiscount() {
        Item item = new Item("laptop", BigDecimal.valueOf(10), "hash1");
        BasketItem basketItem = new BasketItem(item, 1);
        Basket basket = new Basket(new ArrayList<>(List.of(basketItem)), UUID.randomUUID().toString());
        basket.getSummaryInfo().setUsedCard(new ArrayList<>());
        ItemRepository itemRepository = sampleItemRepository(item);
        BasketRepository basketItemRepository = sampleBasketRepository(basket);
        DiscountCard discount = new DiscountCard("cardHash", "laptop",
                TypeOfDiscount.CONSTANT, BigDecimal.TEN);
        DiscountCardRepository discountCardRepository = new DiscountCardRepositoryTest(List.of(discount));

        BasketActions basketActions = new ServiceConfiguration().basketActionsTest(basketItemRepository, itemRepository
                , discountCardRepository, basket);

        //when
        ServiceDiscountCardResultDto actualResult = basketActions.addDiscount("cardHash");
        //then
        ServiceDiscountCardResultDto expectedResult = new ServiceDiscountCardResultDto(List.of("everything is fine", "discount has been added"), discount);
        assertThat(actualResult).isEqualTo(expectedResult);
    }
}