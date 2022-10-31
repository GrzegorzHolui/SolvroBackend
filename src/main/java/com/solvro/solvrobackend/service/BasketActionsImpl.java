package com.solvro.solvrobackend.service;

import com.solvro.solvrobackend.Repository.BasketRepository;
import com.solvro.solvrobackend.Repository.ItemRepository;
import com.solvro.solvrobackend.model.BasketItem;
import com.solvro.solvrobackend.dto.ServiceResultDto;
import com.solvro.solvrobackend.model.DeliveryType;
import com.solvro.solvrobackend.model.DiscountCard;
import com.solvro.solvrobackend.model.SummaryInfo;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
//@Service
public class BasketActionsImpl implements BasketActions {

    BasketRepository basketItemRepository;
    ItemRepository itemRepository;


    BasketAndItemValidator basketAndItemValidator;
    ValidatorMessageConverter numberValidatorMessageConverter;
    BasketItemSaver basketItemSaver;
    BasketItemDeleter basketItemDeleter;
    BasketItemQuantityChanger basketItemQuantityChanger;
    BasketDeliveryTypeSetter basketDeliveryTypeSetter;
    BasketDiscountSetter basketDiscountSetter;
    BasketSummaryInfoMaker basketSummaryInfoMaker;

    @Override
    public ServiceResultDto addItem(String basketHash, String itemHash, int itemQuantity) {
        List<BasketAndItemValidatorMessage> enumValidatorMessage = basketAndItemValidator.validateBasketAndItem(basketHash, itemHash);
        List<String> validatorMessage = numberValidatorMessageConverter
                .convertValidatorMessageToString(enumValidatorMessage);
        if (basketAndItemValidator.isBasketAndItemAfterValidationAcceptable(enumValidatorMessage)) {
            BasketItem basketItem = basketItemSaver.saveBasketItem(basketHash, itemHash, itemQuantity);
            return new ServiceResultDto(validatorMessage, basketItem);
        }
        return new ServiceResultDto(validatorMessage, null);
    }

    @Override
    public ServiceResultDto deleteItem(String basketHash, String itemHash) {
        List<BasketAndItemValidatorMessage> enumValidatorMessage = basketAndItemValidator.validateBasketAndItem(basketHash, itemHash);
        List<String> validatorMessage = numberValidatorMessageConverter
                .convertValidatorMessageToString(enumValidatorMessage);
        if (basketAndItemValidator.isBasketAndItemAfterValidationAcceptable(enumValidatorMessage)) {
            BasketItem resultDeletion = basketItemDeleter.deleteBasketItem(basketHash, itemHash, validatorMessage);
            return new ServiceResultDto(validatorMessage, resultDeletion);
        }
        return new ServiceResultDto(validatorMessage, null);
    }


    @Override
    public ServiceResultDto changeAmountOfProduct(String basketHash, String itemHash, int newQuantity) {
        List<BasketAndItemValidatorMessage> enumValidatorMessage = basketAndItemValidator.validateBasketAndItem(basketHash, itemHash);
        List<String> validatorMessage = numberValidatorMessageConverter
                .convertValidatorMessageToString(enumValidatorMessage);
        if (basketAndItemValidator.isBasketAndItemAfterValidationAcceptable(enumValidatorMessage)) {
            BasketItem changerQuantityResult = basketItemQuantityChanger.changeQuantity(basketHash, itemHash, newQuantity, validatorMessage);
            return new ServiceResultDto(validatorMessage, changerQuantityResult);
        }
        return new ServiceResultDto(validatorMessage, null);
    }

    @Override
    public ServiceResultDto setDeliveryType(String basketHash, DeliveryType deliveryType) {
        List<BasketAndItemValidatorMessage> enumValidatorMessage = basketAndItemValidator.validateBasket(basketHash);
        List<String> validatorMessage = numberValidatorMessageConverter
                .convertValidatorMessageToString(enumValidatorMessage);
        if (basketAndItemValidator.isBasketAndItemAfterValidationAcceptable(enumValidatorMessage)) {
            DeliveryType deliveryTypeResult = basketDeliveryTypeSetter
                    .setBasketDeliveryType(basketHash, deliveryType);
            return new ServiceResultDto(validatorMessage, deliveryTypeResult);
        }
        return new ServiceResultDto(validatorMessage, null);
    }

    @Override
    public ServiceResultDto setDiscount(String basketHash, String discountCardHash) {
        List<BasketAndItemValidatorMessage> enumValidatorMessage = basketAndItemValidator.validateBasket(basketHash);
        List<String> validatorMessage = numberValidatorMessageConverter
                .convertValidatorMessageToString(enumValidatorMessage);
        if (basketAndItemValidator.isBasketAndItemAfterValidationAcceptable(enumValidatorMessage)) {
            DiscountCard discount = basketDiscountSetter.setBasketDiscount(basketHash, discountCardHash);
            return new ServiceResultDto(validatorMessage, discount);
        }
        return new ServiceResultDto(validatorMessage, null);
    }

    @Override
    public ServiceResultDto getInformationAboutBasket(String basketHash) {
        List<BasketAndItemValidatorMessage> enumValidatorMessage = basketAndItemValidator.validateBasket(basketHash);
        List<String> validatorMessage = numberValidatorMessageConverter
                .convertValidatorMessageToString(enumValidatorMessage);
        if (basketAndItemValidator.isBasketAndItemAfterValidationAcceptable(enumValidatorMessage)) {
            SummaryInfo summaryInfo = basketSummaryInfoMaker.generateSummaryInfo(basketHash);
            return new ServiceResultDto(validatorMessage, summaryInfo);
        }
        return new ServiceResultDto(validatorMessage, null);
    }
}