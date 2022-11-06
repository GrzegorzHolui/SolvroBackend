package com.solvro.solvrobackend.service;

import com.solvro.solvrobackend.dto.ServiceChangeQuantityResultDto;
import com.solvro.solvrobackend.dto.ServiceCrudResultDto;
import com.solvro.solvrobackend.dto.ServiceDiscountCardResultDto;
import com.solvro.solvrobackend.dto.ServiceSetDeliveryTypeResultDto;
import com.solvro.solvrobackend.model.Basket;
import com.solvro.solvrobackend.model.BasketItem;
import com.solvro.solvrobackend.dto.ServiceSummaryResultDto;
import com.solvro.solvrobackend.model.DeliveryType;
import com.solvro.solvrobackend.model.DiscountCard;
import com.solvro.solvrobackend.model.SummaryInfo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class BasketActionsImpl implements BasketActions {

    Basket basket;
    BasketAndItemValidator basketAndItemValidator;
    ValidatorMessageConverter numberValidatorMessageConverter;
    BasketItemSaver basketItemSaver;
    BasketItemDeleter basketItemDeleter;
    BasketItemQuantityChanger basketItemQuantityChanger;
    BasketDeliveryTypeSetter basketDeliveryTypeSetter;
    BasketDiscountSetter basketDiscountSetter;
    BasketSummaryInfoMaker basketSummaryInfoMaker;


    @Override
    public ServiceCrudResultDto addItem(String itemHash, int itemQuantity) {
        List<BasketAndItemValidatorMessage> enumValidatorMessage = basketAndItemValidator.validateItem(itemHash);
        List<String> validatorMessage = numberValidatorMessageConverter
                .convertValidatorMessageToString(enumValidatorMessage);
        if (basketAndItemValidator.isBasketAndItemAfterValidationAcceptable(enumValidatorMessage)) {
            BasketItem basketItem = basketItemSaver.saveBasketItem(itemHash, itemQuantity);
            validatorMessage.add("item has been added");
            return new ServiceCrudResultDto(validatorMessage, basketItem);
        }
        return new ServiceCrudResultDto(validatorMessage, null);
    }

    @Override
    public ServiceCrudResultDto deleteItem(String itemHash) {
        List<BasketAndItemValidatorMessage> enumValidatorMessage = basketAndItemValidator.validateItem(itemHash);
        List<String> validatorMessage = numberValidatorMessageConverter
                .convertValidatorMessageToString(enumValidatorMessage);
        if (basketAndItemValidator.isBasketAndItemAfterValidationAcceptable(enumValidatorMessage)) {
            BasketItem resultDeletion = basketItemDeleter.deleteBasketItem(itemHash, validatorMessage);
            if (!validatorMessage.contains("This product is not in your basket")) {
                validatorMessage.add("item has been deleted");
            }
            return new ServiceCrudResultDto(validatorMessage, resultDeletion);
        }
        return new ServiceCrudResultDto(validatorMessage, null);
    }


    @Override
    public ServiceChangeQuantityResultDto changeAmountOfProduct(String itemHash, int newQuantity) {
        List<BasketAndItemValidatorMessage> enumValidatorMessage = basketAndItemValidator.validateItem(itemHash);
        List<String> validatorMessage = numberValidatorMessageConverter
                .convertValidatorMessageToString(enumValidatorMessage);
        if (basketAndItemValidator.isBasketAndItemAfterValidationAcceptable(enumValidatorMessage)) {
            BasketItem changerQuantityResult = basketItemQuantityChanger.changeQuantity(itemHash, newQuantity, validatorMessage);
            validatorMessage.add("item has another quantity");
            return new ServiceChangeQuantityResultDto(validatorMessage, changerQuantityResult.getQuantity());
        }
        return new ServiceChangeQuantityResultDto(validatorMessage, null);
    }

    @Override
    public ServiceSetDeliveryTypeResultDto setDeliveryType(DeliveryType deliveryType) {
        List<String> validatorMessage = new ArrayList<>();
        DeliveryType deliveryTypeResult = basketDeliveryTypeSetter
                .setBasketDeliveryType(deliveryType);
        validatorMessage.add("everything is fine");
        validatorMessage.add("DeliveryType has been added");
        return new ServiceSetDeliveryTypeResultDto(validatorMessage, deliveryTypeResult);
    }

    @Override
    public ServiceDiscountCardResultDto addDiscount(String discountCardHash) {
        DiscountCard discount = basketDiscountSetter.setBasketDiscount(discountCardHash);
        List<String> validatorMessage = new ArrayList<>(List.of("everything is fine", "discount has been added"));
        return new ServiceDiscountCardResultDto(validatorMessage, discount);
    }

    @Override
    public ServiceSummaryResultDto getInformationAboutBasket() {
        SummaryInfo summaryInfo = basketSummaryInfoMaker.generateSummaryInfo();
        return new ServiceSummaryResultDto(List.of("everything is fine"), summaryInfo);
    }

}