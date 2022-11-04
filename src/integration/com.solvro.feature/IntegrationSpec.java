package com.solvro.feature;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvro.solvrobackend.controllers.RequestsDto.ItemAdderRequestDto;
import com.solvro.solvrobackend.controllers.RequestsDto.RemoveItemRequestDto;
import com.solvro.solvrobackend.controllers.RequestsDto.SetterDeliveryTypeRequestDto;
import com.solvro.solvrobackend.controllers.RequestsDto.AdderDiscountRequestDto;
import com.solvro.solvrobackend.controllers.RequestsDto.SetterQuantityRequestDto;
import com.solvro.solvrobackend.dto.BasketResultDto;
import com.solvro.solvrobackend.dto.ServiceChangeQuantityResultDto;
import com.solvro.solvrobackend.dto.ServiceCrudResultDto;
import com.solvro.solvrobackend.dto.ServiceDiscountCardResultDto;
import com.solvro.solvrobackend.dto.ServiceSetDeliveryTypeResultDto;
import com.solvro.solvrobackend.dto.ServiceSummaryResultDto;
import com.solvro.solvrobackend.model.DeliveryType;
import com.solvro.solvrobackend.model.DiscountCard;
import com.solvro.solvrobackend.model.Item;
import com.solvro.solvrobackend.model.SummaryInfo;
import com.solvro.solvrobackend.model.TypeOfDiscount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
public class IntegrationSpec extends BaseSpecIntegration implements DefaultMethods {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

//    #Happy Path for all functions
//    User take yours basket
//    User add three things
//    User delete one thing
//    User changes the quantity of one product
//    User set delivery Type
//    User add discount
//    User ask for summary

    @Test
    public void shouldUserUseAllFunctions() throws Exception {

        MvcResult mvcResultOfPostAddBasket = postAddBasket(mockMvc);
        BasketResultDto basketResultDto = objectMapper.readValue
                (mvcResultOfPostAddBasket.getResponse().getContentAsString(), BasketResultDto.class);

        assertThat(mvcResultOfPostAddBasket.getResponse().getStatus()).isEqualTo(200);
        assertThat(basketResultDto.message()).isEqualTo("new Basket has been added");

        String basketHash = basketResultDto.basketHash();

        ItemAdderRequestDto laptopAdderRequestDto = ItemAdderRequestDto.builder().
                basketHash(basketHash).
                itemHash("laptopHash").
                itemQuantity(1).build();

        Item expectedLaptop = Item.builder()
                .nameOfProduct("laptop")
                .priceForOneItem(BigDecimal.TEN)
                .productHash("laptopHash")
                .build();

        MvcResult mvcResultPostAddItemLaptop = postAddItem(mockMvc, laptopAdderRequestDto);

        ServiceCrudResultDto postLaptopResult = objectMapper.readValue(mvcResultPostAddItemLaptop.getResponse()
                .getContentAsString(), ServiceCrudResultDto.class);

        assertThat(mvcResultPostAddItemLaptop.getResponse().getStatus()).isEqualTo(200);
        assertThat(postLaptopResult.message()).isEqualTo(List.of("everything_is_fine", "item has been added"));
        assertThat(postLaptopResult.basketItem().getItem()).isEqualTo(expectedLaptop);

        ItemAdderRequestDto torchAdderRequestDto = ItemAdderRequestDto.builder().
                basketHash(basketHash).
                itemHash("torchHash").
                itemQuantity(1).build();

        Item expectedTorch = Item.builder()
                .nameOfProduct("torch")
                .priceForOneItem(BigDecimal.ONE)
                .productHash("torchHash")
                .build();

        MvcResult mvcResultPostAddItemTorch = postAddItem(mockMvc, torchAdderRequestDto);

        ServiceCrudResultDto postTorchResult = objectMapper.readValue(mvcResultPostAddItemTorch.getResponse()
                .getContentAsString(), ServiceCrudResultDto.class);

        assertThat(mvcResultPostAddItemTorch.getResponse().getStatus()).isEqualTo(200);
        assertThat(postTorchResult.basketItem().getItem()).isEqualTo(expectedTorch);
        assertThat(postTorchResult.message()).isEqualTo(List.of("everything_is_fine", "item has been added"));


        ItemAdderRequestDto pcAdderRequestDto = ItemAdderRequestDto.builder().
                basketHash(basketHash).
                itemHash("pcHash").
                itemQuantity(1).build();

        Item expectedPc = Item.builder()
                .nameOfProduct("pc")
                .priceForOneItem(BigDecimal.TEN)
                .productHash("pcHash")
                .build();

        MvcResult mvcResultPostAddItemPc = postAddItem(mockMvc, pcAdderRequestDto);

        ServiceCrudResultDto postPcResult = objectMapper.readValue(mvcResultPostAddItemPc.getResponse()
                .getContentAsString(), ServiceCrudResultDto.class);

        assertThat(mvcResultPostAddItemPc.getResponse().getStatus()).isEqualTo(200);
        assertThat(postPcResult.message()).isEqualTo(List.of("everything_is_fine", "item has been added"));
        assertThat(postPcResult.basketItem().getItem()).isEqualTo(expectedPc);


        RemoveItemRequestDto removeItemRequestDto = RemoveItemRequestDto.builder()
                .basketHash(basketHash)
                .itemHash("torchHash")
                .build();

        Item expectedDeletedTorch = Item.builder()
                .nameOfProduct("torch")
                .priceForOneItem(BigDecimal.ONE)
                .productHash("torchHash")
                .build();

        MvcResult mvcResultDeleteTorch = deleteItem(mockMvc, removeItemRequestDto);
        ServiceCrudResultDto deleteTorchResult = objectMapper.readValue(mvcResultDeleteTorch.getResponse()
                .getContentAsString(), ServiceCrudResultDto.class);

        assertThat(mvcResultDeleteTorch.getResponse().getStatus()).isEqualTo(200);
        assertThat(deleteTorchResult.message()).isEqualTo(List.of("everything_is_fine", "item has been deleted"));
        assertThat(deleteTorchResult.basketItem().getItem()).isEqualTo(expectedDeletedTorch);

        SetterQuantityRequestDto setterQuantityRequestDto = SetterQuantityRequestDto.builder()
                .basketHash(basketHash)
                .itemHash("laptopHash")
                .newQuantity(10)
                .build();

        MvcResult mvcResultChangeQuantity = changeQuantity(mockMvc, setterQuantityRequestDto);
        ServiceChangeQuantityResultDto changeLaptopQuantityResult = objectMapper.readValue(mvcResultChangeQuantity.getResponse()
                .getContentAsString(), ServiceChangeQuantityResultDto.class);

        assertThat(mvcResultChangeQuantity.getResponse().getStatus()).isEqualTo(200);
        assertThat(changeLaptopQuantityResult.quantity()).isEqualTo(10);
        assertThat(changeLaptopQuantityResult.message()).isEqualTo(List.of("everything_is_fine",
                "item has another quantity"));

        SetterDeliveryTypeRequestDto setterDeliveryTypeRequestDto = SetterDeliveryTypeRequestDto.builder()
                .basketHash(basketHash)
                .deliveryType(DeliveryType.COURIER_DELIVERY_INPOST)
                .build();

        MvcResult mvcResultDeliveryType = setDeliveryType(mockMvc, setterDeliveryTypeRequestDto);
        ServiceSetDeliveryTypeResultDto setDeliveryTypeResult = objectMapper.readValue(mvcResultDeliveryType.getResponse()
                .getContentAsString(), ServiceSetDeliveryTypeResultDto.class);

        assertThat(mvcResultChangeQuantity.getResponse().getStatus()).isEqualTo(200);
        assertThat(setDeliveryTypeResult.deliveryType()).isEqualTo(DeliveryType.COURIER_DELIVERY_INPOST);
        assertThat(setDeliveryTypeResult.message()).isEqualTo(List.of("everything_is_fine", "DeliveryType has been added"));

        AdderDiscountRequestDto adderDiscountRequestDto = AdderDiscountRequestDto.builder()
                .basketHash(basketHash)
                .discountCardHash("laptopCard")
                .build();

        DiscountCard expectedDiscountCard = DiscountCard.builder()
                .cardHash("laptopCard")
                .discountProductName("laptop")
                .typeOfDiscount(TypeOfDiscount.CONSTANT)
                .value(BigDecimal.valueOf(5))
                .build();

        MvcResult mvcResultDiscountSetter = addDiscount(mockMvc, adderDiscountRequestDto);
        ServiceDiscountCardResultDto serviceDiscountCardResult = objectMapper.readValue(mvcResultDiscountSetter.getResponse()
                .getContentAsString(), ServiceDiscountCardResultDto.class);

        assertThat(mvcResultDiscountSetter.getResponse().getStatus()).isEqualTo(200);
        assertThat(serviceDiscountCardResult.discountCard()).isEqualTo(expectedDiscountCard);
        assertThat(serviceDiscountCardResult.message()).isEqualTo(List.of("everything_is_fine", "discount has been added"));

        SummaryInfo expectedResult = SummaryInfo.builder()
                .deliveryType(DeliveryType.COURIER_DELIVERY_INPOST)
                .usedCard(List.of(expectedDiscountCard))
                .priceForProducts(BigDecimal.valueOf(110))
                .finalPrice(BigDecimal.valueOf(125))
                .build();

        MvcResult mvcResultBasketInformationGetter = getBasketInformation(mockMvc, basketHash);
        ServiceSummaryResultDto serviceSummaryResult = objectMapper.readValue(mvcResultBasketInformationGetter.getResponse()
                .getContentAsString(), ServiceSummaryResultDto.class);

        assertThat(mvcResultBasketInformationGetter.getResponse().getStatus()).isEqualTo(200);
        assertThat(serviceSummaryResult.summaryInfo()).isEqualTo(expectedResult);
        assertThat(serviceSummaryResult.message()).isEqualTo(List.of("everything_is_fine"));
    }
}