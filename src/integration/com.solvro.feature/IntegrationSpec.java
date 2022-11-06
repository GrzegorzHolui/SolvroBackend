package com.solvro.feature;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvro.solvrobackend.controllers.requestsdto.AdderDiscountRequestDto;
import com.solvro.solvrobackend.controllers.requestsdto.ItemAdderRequestDto;
import com.solvro.solvrobackend.controllers.requestsdto.RemoveItemRequestDto;
import com.solvro.solvrobackend.controllers.requestsdto.SetterDeliveryTypeRequestDto;
import com.solvro.solvrobackend.controllers.requestsdto.SetterQuantityRequestDto;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
public class IntegrationSpec extends BaseSpecIntegration implements DefaultMethods {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;
//    #Happy Path for all functions
//    User add three items
//    User delete one item
//    User changes the quantity of one product
//    User set delivery Type
//    User add discount
//    User ask for summary

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void shouldUserUseAllFunctionsWithConstantDiscount() throws Exception {
//        given
        ItemAdderRequestDto laptopAdderRequestDto = ItemAdderRequestDto.builder().
                itemHash("laptopHash").
                itemQuantity(1).build();

        Item expectedLaptop = Item.builder()
                .nameOfProduct("laptop")
                .priceForOneItem(BigDecimal.TEN)
                .productHash("laptopHash")
                .build();

        MvcResult mvcResultPostAddItemLaptop = postAddItem(mockMvc, laptopAdderRequestDto);
//      when
        ServiceCrudResultDto postLaptopResult = objectMapper.readValue(mvcResultPostAddItemLaptop.getResponse()
                .getContentAsString(), ServiceCrudResultDto.class);
//      then
        assertThat(mvcResultPostAddItemLaptop.getResponse().getStatus()).isEqualTo(200);
        assertThat(postLaptopResult.message()).isEqualTo(List.of("everything is fine", "item has been added"));
        assertThat(postLaptopResult.basketItem().getItem()).isEqualTo(expectedLaptop);

//        given
        ItemAdderRequestDto torchAdderRequestDto = ItemAdderRequestDto.builder().
                itemHash("torchHash").
                itemQuantity(1).build();

        Item expectedTorch = Item.builder()
                .nameOfProduct("torch")
                .priceForOneItem(BigDecimal.ONE)
                .productHash("torchHash")
                .build();

        MvcResult mvcResultPostAddItemTorch = postAddItem(mockMvc, torchAdderRequestDto);
//      when
        ServiceCrudResultDto postTorchResult = objectMapper.readValue(mvcResultPostAddItemTorch.getResponse()
                .getContentAsString(), ServiceCrudResultDto.class);

//      then
        assertThat(mvcResultPostAddItemTorch.getResponse().getStatus()).isEqualTo(200);
        assertThat(postTorchResult.basketItem().getItem()).isEqualTo(expectedTorch);
        assertThat(postTorchResult.message()).isEqualTo(List.of("everything is fine", "item has been added"));

//        given
        ItemAdderRequestDto pcAdderRequestDto = ItemAdderRequestDto.builder().
                itemHash("pcHash").
                itemQuantity(1).build();

        Item expectedPc = Item.builder()
                .nameOfProduct("pc")
                .priceForOneItem(BigDecimal.TEN)
                .productHash("pcHash")
                .build();

        MvcResult mvcResultPostAddItemPc = postAddItem(mockMvc, pcAdderRequestDto);

//        when
        ServiceCrudResultDto postPcResult = objectMapper.readValue(mvcResultPostAddItemPc.getResponse()
                .getContentAsString(), ServiceCrudResultDto.class);

//      then
        assertThat(mvcResultPostAddItemPc.getResponse().getStatus()).isEqualTo(200);
        assertThat(postPcResult.message()).isEqualTo(List.of("everything is fine", "item has been added"));
        assertThat(postPcResult.basketItem().getItem()).isEqualTo(expectedPc);

//        given
        RemoveItemRequestDto removeItemRequestDto = RemoveItemRequestDto.builder()
                .itemHash("torchHash")
                .build();
        Item expectedDeletedTorch = Item.builder()
                .nameOfProduct("torch")
                .priceForOneItem(BigDecimal.ONE)
                .productHash("torchHash")
                .build();

//        when
        MvcResult mvcResultDeleteTorch = deleteItem(mockMvc, removeItemRequestDto);
        ServiceCrudResultDto deleteTorchResult = objectMapper.readValue(mvcResultDeleteTorch.getResponse()
                .getContentAsString(), ServiceCrudResultDto.class);

//        then
        assertThat(mvcResultDeleteTorch.getResponse().getStatus()).isEqualTo(200);
        assertThat(deleteTorchResult.message()).isEqualTo(List.of("everything is fine", "item has been deleted"));
        assertThat(deleteTorchResult.basketItem().getItem()).isEqualTo(expectedDeletedTorch);

//        given
        SetterQuantityRequestDto setterQuantityRequestDto = SetterQuantityRequestDto.builder()
                .itemHash("laptopHash")
                .newQuantity(10)
                .build();

//        when
        MvcResult mvcResultChangeQuantity = changeQuantity(mockMvc, setterQuantityRequestDto);
        ServiceChangeQuantityResultDto changeLaptopQuantityResult = objectMapper.readValue(mvcResultChangeQuantity.getResponse()
                .getContentAsString(), ServiceChangeQuantityResultDto.class);

//        then
        assertThat(mvcResultChangeQuantity.getResponse().getStatus()).isEqualTo(200);
        assertThat(changeLaptopQuantityResult.quantity()).isEqualTo(10);
        assertThat(changeLaptopQuantityResult.message()).isEqualTo(List.of("everything is fine",
                "item has another quantity"));

//        given
        SetterDeliveryTypeRequestDto setterDeliveryTypeRequestDto = SetterDeliveryTypeRequestDto.builder()
                .deliveryType(DeliveryType.COURIER_DELIVERY_INPOST)
                .build();

//        when
        MvcResult mvcResultDeliveryType = setDeliveryType(mockMvc, setterDeliveryTypeRequestDto);
        ServiceSetDeliveryTypeResultDto setDeliveryTypeResult = objectMapper.readValue(mvcResultDeliveryType.getResponse()
                .getContentAsString(), ServiceSetDeliveryTypeResultDto.class);

//        then
        assertThat(mvcResultChangeQuantity.getResponse().getStatus()).isEqualTo(200);
        assertThat(setDeliveryTypeResult.deliveryType()).isEqualTo(DeliveryType.COURIER_DELIVERY_INPOST);
        assertThat(setDeliveryTypeResult.message()).isEqualTo(List.of("everything is fine", "DeliveryType has been added"));

//        given
        AdderDiscountRequestDto laptopCard = AdderDiscountRequestDto
                .builder()
                .discountCardHash("laptopCard")
                .build();

        DiscountCard expectedDiscountCard = DiscountCard.builder()
                .cardHash("laptopCard")
                .discountProductName("laptop")
                .typeOfDiscount(TypeOfDiscount.CONSTANT)
                .value(BigDecimal.valueOf(5))
                .build();

//        when
        MvcResult mvcResultDiscountSetter = addDiscount(mockMvc, laptopCard);
        ServiceDiscountCardResultDto serviceDiscountCardResult = objectMapper.readValue(mvcResultDiscountSetter.getResponse()
                .getContentAsString(), ServiceDiscountCardResultDto.class);

//        then
        assertThat(mvcResultDiscountSetter.getResponse().getStatus()).isEqualTo(200);
        assertThat(serviceDiscountCardResult.discountCard()).isEqualTo(expectedDiscountCard);
        assertThat(serviceDiscountCardResult.message()).isEqualTo(List.of("everything is fine", "discount has been added"));

//        given
        SummaryInfo expectedResult = SummaryInfo.builder()
                .deliveryType(DeliveryType.COURIER_DELIVERY_INPOST)
                .usedCard(List.of(expectedDiscountCard))
                .priceForProducts(BigDecimal.valueOf(110))
                .finalPrice(BigDecimal.valueOf(125))
                .build();

//        when
        MvcResult mvcResultBasketInformationGetter = getBasketInformation(mockMvc);
        ServiceSummaryResultDto serviceSummaryResult = objectMapper.readValue(mvcResultBasketInformationGetter.getResponse()
                .getContentAsString(), ServiceSummaryResultDto.class);

//        then
        assertThat(mvcResultBasketInformationGetter.getResponse().getStatus()).isEqualTo(200);
        assertThat(serviceSummaryResult.summaryInfo()).isEqualTo(expectedResult);
        assertThat(serviceSummaryResult.message()).isEqualTo(List.of("everything is fine"));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldUserUseAllFunctionsWithoutDiscount() throws Exception {
//        given
        ItemAdderRequestDto laptopAdderRequestDto = ItemAdderRequestDto.builder().
                itemHash("laptopHash").
                itemQuantity(1).build();

        Item expectedLaptop = Item.builder()
                .nameOfProduct("laptop")
                .priceForOneItem(BigDecimal.TEN)
                .productHash("laptopHash")
                .build();

//        when
        MvcResult mvcResultPostAddItemLaptop = postAddItem(mockMvc, laptopAdderRequestDto);
        ServiceCrudResultDto postLaptopResult = objectMapper.readValue(mvcResultPostAddItemLaptop.getResponse()
                .getContentAsString(), ServiceCrudResultDto.class);

//        then
        assertThat(mvcResultPostAddItemLaptop.getResponse().getStatus()).isEqualTo(200);
        assertThat(postLaptopResult.message()).isEqualTo(List.of("everything is fine", "item has been added"));
        assertThat(postLaptopResult.basketItem().getItem()).isEqualTo(expectedLaptop);

//        given
        ItemAdderRequestDto torchAdderRequestDto = ItemAdderRequestDto.builder().
                itemHash("torchHash").
                itemQuantity(1).build();

        Item expectedTorch = Item.builder()
                .nameOfProduct("torch")
                .priceForOneItem(BigDecimal.ONE)
                .productHash("torchHash")
                .build();

//        when
        MvcResult mvcResultPostAddItemTorch = postAddItem(mockMvc, torchAdderRequestDto);
        ServiceCrudResultDto postTorchResult = objectMapper.readValue(mvcResultPostAddItemTorch.getResponse()
                .getContentAsString(), ServiceCrudResultDto.class);

//        then
        assertThat(mvcResultPostAddItemTorch.getResponse().getStatus()).isEqualTo(200);
        assertThat(postTorchResult.basketItem().getItem()).isEqualTo(expectedTorch);
        assertThat(postTorchResult.message()).isEqualTo(List.of("everything is fine", "item has been added"));

//        given
        ItemAdderRequestDto pcAdderRequestDto = ItemAdderRequestDto.builder().
                itemHash("pcHash").
                itemQuantity(1).build();

        Item expectedPc = Item.builder()
                .nameOfProduct("pc")
                .priceForOneItem(BigDecimal.TEN)
                .productHash("pcHash")
                .build();

//        when
        MvcResult mvcResultPostAddItemPc = postAddItem(mockMvc, pcAdderRequestDto);
        ServiceCrudResultDto postPcResult = objectMapper.readValue(mvcResultPostAddItemPc.getResponse()
                .getContentAsString(), ServiceCrudResultDto.class);

//        then
        assertThat(mvcResultPostAddItemPc.getResponse().getStatus()).isEqualTo(200);
        assertThat(postPcResult.message()).isEqualTo(List.of("everything is fine", "item has been added"));
        assertThat(postPcResult.basketItem().getItem()).isEqualTo(expectedPc);

//        given
        RemoveItemRequestDto removeItemRequestDto = RemoveItemRequestDto.builder()
                .itemHash("torchHash")
                .build();

        Item expectedDeletedTorch = Item.builder()
                .nameOfProduct("torch")
                .priceForOneItem(BigDecimal.ONE)
                .productHash("torchHash")
                .build();

//        when
        MvcResult mvcResultDeleteTorch = deleteItem(mockMvc, removeItemRequestDto);
        ServiceCrudResultDto deleteTorchResult = objectMapper.readValue(mvcResultDeleteTorch.getResponse()
                .getContentAsString(), ServiceCrudResultDto.class);

//        then
        assertThat(mvcResultDeleteTorch.getResponse().getStatus()).isEqualTo(200);
        assertThat(deleteTorchResult.message()).isEqualTo(List.of("everything is fine", "item has been deleted"));
        assertThat(deleteTorchResult.basketItem().getItem()).isEqualTo(expectedDeletedTorch);

//        given
        SetterQuantityRequestDto setterQuantityRequestDto = SetterQuantityRequestDto.builder()
                .itemHash("laptopHash")
                .newQuantity(10)
                .build();

//        when
        MvcResult mvcResultChangeQuantity = changeQuantity(mockMvc, setterQuantityRequestDto);
        ServiceChangeQuantityResultDto changeLaptopQuantityResult = objectMapper.readValue(mvcResultChangeQuantity.getResponse()
                .getContentAsString(), ServiceChangeQuantityResultDto.class);

//        then
        assertThat(mvcResultChangeQuantity.getResponse().getStatus()).isEqualTo(200);
        assertThat(changeLaptopQuantityResult.quantity()).isEqualTo(10);
        assertThat(changeLaptopQuantityResult.message()).isEqualTo(List.of("everything is fine",
                "item has another quantity"));

//        given
        SetterDeliveryTypeRequestDto setterDeliveryTypeRequestDto = SetterDeliveryTypeRequestDto.builder()
                .deliveryType(DeliveryType.COURIER_DELIVERY_INPOST)
                .build();

//        when
        MvcResult mvcResultDeliveryType = setDeliveryType(mockMvc, setterDeliveryTypeRequestDto);
        ServiceSetDeliveryTypeResultDto setDeliveryTypeResult = objectMapper.readValue(mvcResultDeliveryType.getResponse()
                .getContentAsString(), ServiceSetDeliveryTypeResultDto.class);

//        then
        assertThat(mvcResultChangeQuantity.getResponse().getStatus()).isEqualTo(200);
        assertThat(setDeliveryTypeResult.deliveryType()).isEqualTo(DeliveryType.COURIER_DELIVERY_INPOST);
        assertThat(setDeliveryTypeResult.message()).isEqualTo(List.of("everything is fine", "DeliveryType has been added"));

//        given
        SummaryInfo expectedResult = SummaryInfo.builder()
                .deliveryType(DeliveryType.COURIER_DELIVERY_INPOST)
                .usedCard(new ArrayList<>())
                .priceForProducts(BigDecimal.valueOf(110))
                .finalPrice(BigDecimal.valueOf(130))
                .build();

//        when
        MvcResult mvcResultBasketInformationGetter = getBasketInformation(mockMvc);
        ServiceSummaryResultDto serviceSummaryResult = objectMapper.readValue(mvcResultBasketInformationGetter.getResponse()
                .getContentAsString(), ServiceSummaryResultDto.class);

//        then
        assertThat(mvcResultBasketInformationGetter.getResponse().getStatus()).isEqualTo(200);
        assertThat(serviceSummaryResult.summaryInfo()).isEqualTo(expectedResult);
        assertThat(serviceSummaryResult.message()).isEqualTo(List.of("everything is fine"));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldUserUseAllFunctionsWithPercentDiscount() throws Exception {

//        given
        ItemAdderRequestDto laptopAdderRequestDto = ItemAdderRequestDto.builder().
                itemHash("laptopHash").
                itemQuantity(1).build();

        Item expectedLaptop = Item.builder()
                .nameOfProduct("laptop")
                .priceForOneItem(BigDecimal.TEN)
                .productHash("laptopHash")
                .build();

//        when
        MvcResult mvcResultPostAddItemLaptop = postAddItem(mockMvc, laptopAdderRequestDto);
        ServiceCrudResultDto postLaptopResult = objectMapper.readValue(mvcResultPostAddItemLaptop.getResponse()
                .getContentAsString(), ServiceCrudResultDto.class);

//        then
        assertThat(mvcResultPostAddItemLaptop.getResponse().getStatus()).isEqualTo(200);
        assertThat(postLaptopResult.message()).isEqualTo(List.of("everything is fine", "item has been added"));
        assertThat(postLaptopResult.basketItem().getItem()).isEqualTo(expectedLaptop);

//        given
        ItemAdderRequestDto torchAdderRequestDto = ItemAdderRequestDto.builder().
                itemHash("torchHash").
                itemQuantity(1).build();

        Item expectedTorch = Item.builder()
                .nameOfProduct("torch")
                .priceForOneItem(BigDecimal.ONE)
                .productHash("torchHash")
                .build();

//        when
        MvcResult mvcResultPostAddItemTorch = postAddItem(mockMvc, torchAdderRequestDto);
        ServiceCrudResultDto postTorchResult = objectMapper.readValue(mvcResultPostAddItemTorch.getResponse()
                .getContentAsString(), ServiceCrudResultDto.class);

//        then
        assertThat(mvcResultPostAddItemTorch.getResponse().getStatus()).isEqualTo(200);
        assertThat(postTorchResult.basketItem().getItem()).isEqualTo(expectedTorch);
        assertThat(postTorchResult.message()).isEqualTo(List.of("everything is fine", "item has been added"));

//        given
        ItemAdderRequestDto pcAdderRequestDto = ItemAdderRequestDto.builder().
                itemHash("pcHash").
                itemQuantity(1).build();

        Item expectedPc = Item.builder()
                .nameOfProduct("pc")
                .priceForOneItem(BigDecimal.TEN)
                .productHash("pcHash")
                .build();

//        when
        MvcResult mvcResultPostAddItemPc = postAddItem(mockMvc, pcAdderRequestDto);
        ServiceCrudResultDto postPcResult = objectMapper.readValue(mvcResultPostAddItemPc.getResponse()
                .getContentAsString(), ServiceCrudResultDto.class);

//        then
        assertThat(mvcResultPostAddItemPc.getResponse().getStatus()).isEqualTo(200);
        assertThat(postPcResult.message()).isEqualTo(List.of("everything is fine", "item has been added"));
        assertThat(postPcResult.basketItem().getItem()).isEqualTo(expectedPc);

//        given
        RemoveItemRequestDto removeItemRequestDto = RemoveItemRequestDto.builder()
                .itemHash("torchHash")
                .build();

        Item expectedDeletedTorch = Item.builder()
                .nameOfProduct("torch")
                .priceForOneItem(BigDecimal.ONE)
                .productHash("torchHash")
                .build();

//        when
        MvcResult mvcResultDeleteTorch = deleteItem(mockMvc, removeItemRequestDto);
        ServiceCrudResultDto deleteTorchResult = objectMapper.readValue(mvcResultDeleteTorch.getResponse()
                .getContentAsString(), ServiceCrudResultDto.class);

//        then
        assertThat(mvcResultDeleteTorch.getResponse().getStatus()).isEqualTo(200);
        assertThat(deleteTorchResult.message()).isEqualTo(List.of("everything is fine", "item has been deleted"));
        assertThat(deleteTorchResult.basketItem().getItem()).isEqualTo(expectedDeletedTorch);

//        given
        SetterQuantityRequestDto setterQuantityRequestDto = SetterQuantityRequestDto.builder()
                .itemHash("laptopHash")
                .newQuantity(10)
                .build();

//        when
        MvcResult mvcResultChangeQuantity = changeQuantity(mockMvc, setterQuantityRequestDto);
        ServiceChangeQuantityResultDto changeLaptopQuantityResult = objectMapper.readValue(mvcResultChangeQuantity.getResponse()
                .getContentAsString(), ServiceChangeQuantityResultDto.class);

//        then
        assertThat(mvcResultChangeQuantity.getResponse().getStatus()).isEqualTo(200);
        assertThat(changeLaptopQuantityResult.quantity()).isEqualTo(10);
        assertThat(changeLaptopQuantityResult.message()).isEqualTo(List.of("everything is fine",
                "item has another quantity"));

//        given
        AdderDiscountRequestDto pcDiscardHash = AdderDiscountRequestDto
                .builder()
                .discountCardHash("pcCard")
                .build();

        DiscountCard expectedDiscountCard = DiscountCard.builder()
                .cardHash("pcCard")
                .discountProductName("pc")
                .typeOfDiscount(TypeOfDiscount.PERCENT)
                .value(BigDecimal.valueOf(0.5))
                .build();

//        when
        MvcResult mvcResultDiscountSetter = addDiscount(mockMvc, pcDiscardHash);
        ServiceDiscountCardResultDto serviceDiscountCardResult = objectMapper.readValue(mvcResultDiscountSetter.getResponse()
                .getContentAsString(), ServiceDiscountCardResultDto.class);

//        then
        assertThat(mvcResultDiscountSetter.getResponse().getStatus()).isEqualTo(200);
        assertThat(serviceDiscountCardResult.discountCard()).isEqualTo(expectedDiscountCard);
        assertThat(serviceDiscountCardResult.message()).isEqualTo(List.of("everything is fine", "discount has been added"));

//      given
        SetterDeliveryTypeRequestDto setterDeliveryTypeRequestDto = SetterDeliveryTypeRequestDto.builder()
                .deliveryType(DeliveryType.COURIER_DELIVERY_INPOST)
                .build();

//      when
        MvcResult mvcResultDeliveryType = setDeliveryType(mockMvc, setterDeliveryTypeRequestDto);
        ServiceSetDeliveryTypeResultDto setDeliveryTypeResult = objectMapper.readValue(mvcResultDeliveryType.getResponse()
                .getContentAsString(), ServiceSetDeliveryTypeResultDto.class);

//        then
        assertThat(mvcResultChangeQuantity.getResponse().getStatus()).isEqualTo(200);
        assertThat(setDeliveryTypeResult.deliveryType()).isEqualTo(DeliveryType.COURIER_DELIVERY_INPOST);
        assertThat(setDeliveryTypeResult.message()).isEqualTo(List.of("everything is fine", "DeliveryType has been added"));

//      given
        SummaryInfo expectedResult = SummaryInfo.builder()
                .deliveryType(DeliveryType.COURIER_DELIVERY_INPOST)
                .usedCard(new ArrayList<>(List.of(expectedDiscountCard)))
                .priceForProducts(BigDecimal.valueOf(110))
                .finalPrice(BigDecimal.valueOf(125.0))
                .build();

//        when
        MvcResult mvcResultBasketInformationGetter = getBasketInformation(mockMvc);
        ServiceSummaryResultDto serviceSummaryResult = objectMapper.readValue(mvcResultBasketInformationGetter.getResponse()
                .getContentAsString(), ServiceSummaryResultDto.class);

//        then
        assertThat(mvcResultBasketInformationGetter.getResponse().getStatus()).isEqualTo(200);
        assertThat(serviceSummaryResult.summaryInfo()).isEqualTo(expectedResult);
        assertThat(serviceSummaryResult.message()).isEqualTo(List.of("everything is fine"));
    }
}