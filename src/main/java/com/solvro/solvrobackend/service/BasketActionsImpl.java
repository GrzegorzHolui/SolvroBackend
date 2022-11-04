package com.solvro.solvrobackend.service;

import com.solvro.solvrobackend.dto.ServiceChangeQuantityResultDto;
import com.solvro.solvrobackend.dto.ServiceCrudResultDto;
import com.solvro.solvrobackend.dto.ServiceDiscountCardResultDto;
import com.solvro.solvrobackend.dto.ServiceSetDeliveryTypeResultDto;
import com.solvro.solvrobackend.model.BasketItem;
import com.solvro.solvrobackend.dto.ServiceSummaryResultDto;
import com.solvro.solvrobackend.model.DeliveryType;
import com.solvro.solvrobackend.model.DiscountCard;
import com.solvro.solvrobackend.model.SummaryInfo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class BasketActionsImpl implements BasketActions {
    BasketAndItemValidator basketAndItemValidator;
    ValidatorMessageConverter numberValidatorMessageConverter;
    BasketItemSaver basketItemSaver;
    BasketItemDeleter basketItemDeleter;
    BasketItemQuantityChanger basketItemQuantityChanger;
    BasketDeliveryTypeSetter basketDeliveryTypeSetter;
    BasketDiscountSetter basketDiscountSetter;
    BasketSummaryInfoMaker basketSummaryInfoMaker;


    @Override
    public ServiceCrudResultDto addItem(String basketHash, String itemHash, int itemQuantity) {
        List<BasketAndItemValidatorMessage> enumValidatorMessage = basketAndItemValidator.validateBasketAndItem(basketHash, itemHash);
        List<String> validatorMessage = numberValidatorMessageConverter
                .convertValidatorMessageToString(enumValidatorMessage);
        if (basketAndItemValidator.isBasketAndItemAfterValidationAcceptable(enumValidatorMessage)) {
            BasketItem basketItem = basketItemSaver.saveBasketItem(basketHash, itemHash, itemQuantity);
            validatorMessage.add("item has been added");
            return new ServiceCrudResultDto(validatorMessage, basketItem);
        }
        return new ServiceCrudResultDto(validatorMessage, null);
    }

    @Override
    public ServiceCrudResultDto deleteItem(String basketHash, String itemHash) {
        List<BasketAndItemValidatorMessage> enumValidatorMessage = basketAndItemValidator.validateBasketAndItem(basketHash, itemHash);
        List<String> validatorMessage = numberValidatorMessageConverter
                .convertValidatorMessageToString(enumValidatorMessage);
        if (basketAndItemValidator.isBasketAndItemAfterValidationAcceptable(enumValidatorMessage)) {
            BasketItem resultDeletion = basketItemDeleter.deleteBasketItem(basketHash, itemHash, validatorMessage);
            if (!validatorMessage.contains("This product is not in your basket")) {
                validatorMessage.add("item has been deleted");
            }
            return new ServiceCrudResultDto(validatorMessage, resultDeletion);
        }
        return new ServiceCrudResultDto(validatorMessage, null);
    }


    @Override
    public ServiceChangeQuantityResultDto changeAmountOfProduct(String basketHash, String itemHash, int newQuantity) {
        List<BasketAndItemValidatorMessage> enumValidatorMessage = basketAndItemValidator.validateBasketAndItem(basketHash, itemHash);
        List<String> validatorMessage = numberValidatorMessageConverter
                .convertValidatorMessageToString(enumValidatorMessage);
        if (basketAndItemValidator.isBasketAndItemAfterValidationAcceptable(enumValidatorMessage)) {
            BasketItem changerQuantityResult = basketItemQuantityChanger.changeQuantity(basketHash, itemHash, newQuantity, validatorMessage);
            validatorMessage.add("item has another quantity");
            return new ServiceChangeQuantityResultDto(validatorMessage, changerQuantityResult.getQuantity());
        }
        return new ServiceChangeQuantityResultDto(validatorMessage, null);
    }

    @Override
    public ServiceSetDeliveryTypeResultDto setDeliveryType(String basketHash, DeliveryType deliveryType) {
        List<BasketAndItemValidatorMessage> enumValidatorMessage = basketAndItemValidator.validateBasket(basketHash);
        List<String> validatorMessage = numberValidatorMessageConverter
                .convertValidatorMessageToString(enumValidatorMessage);
        if (basketAndItemValidator.isBasketAndItemAfterValidationAcceptable(enumValidatorMessage)) {
            DeliveryType deliveryTypeResult = basketDeliveryTypeSetter
                    .setBasketDeliveryType(basketHash, deliveryType);
            validatorMessage.add("DeliveryType has been added");
            return new ServiceSetDeliveryTypeResultDto(validatorMessage, deliveryTypeResult);
        }
        return new ServiceSetDeliveryTypeResultDto(validatorMessage, null);
    }

    @Override
    public ServiceDiscountCardResultDto addDiscount(String basketHash, String discountCardHash) {
        List<BasketAndItemValidatorMessage> enumValidatorMessage = basketAndItemValidator.validateBasket(basketHash);
        List<String> validatorMessage = numberValidatorMessageConverter
                .convertValidatorMessageToString(enumValidatorMessage);
        if (basketAndItemValidator.isBasketAndItemAfterValidationAcceptable(enumValidatorMessage)) {
            DiscountCard discount = basketDiscountSetter.setBasketDiscount(basketHash, discountCardHash);
            validatorMessage.add("discount has been added");
            return new ServiceDiscountCardResultDto(validatorMessage, discount);
        }
        return new ServiceDiscountCardResultDto(validatorMessage, null);
    }

    @Override
    public ServiceSummaryResultDto getInformationAboutBasket(String basketHash) {
        List<BasketAndItemValidatorMessage> enumValidatorMessage = basketAndItemValidator.validateBasket(basketHash);
        List<String> validatorMessage = numberValidatorMessageConverter
                .convertValidatorMessageToString(enumValidatorMessage);
        if (basketAndItemValidator.isBasketAndItemAfterValidationAcceptable(enumValidatorMessage)) {
            SummaryInfo summaryInfo = basketSummaryInfoMaker.generateSummaryInfo(basketHash);
            return new ServiceSummaryResultDto(validatorMessage, summaryInfo);
        }
        return new ServiceSummaryResultDto(validatorMessage, null);
    }
}