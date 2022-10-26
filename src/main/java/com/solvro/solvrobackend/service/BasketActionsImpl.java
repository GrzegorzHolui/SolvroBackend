package com.solvro.solvrobackend.service;

import com.solvro.solvrobackend.Repository.BasketRepository;
import com.solvro.solvrobackend.Repository.ItemRepository;
import com.solvro.solvrobackend.model.Basket;
import com.solvro.solvrobackend.model.BasketItem;
import com.solvro.solvrobackend.model.Item;
import com.solvro.solvrobackend.dto.ServiceResultDto;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    private static final int FIRST_INDEX = 0;


    @EventListener(ApplicationReadyEvent.class)
    public void fillDB() {
        Item laptop = itemRepository.save
                (new Item(UUID.fromString("ba0f8c84-d37c-4521-9938-dfd14bc99757")
                        , "laptop", BigDecimal.ONE, "laptopHash"));
        basketItemRepository.save(new Basket(UUID.fromString("dea38381-7ab0-4131-b1f5-23359ca2b834"),
                "basketHash",
                new ArrayList<>()));
    }

    @Override
    public ServiceResultDto addItem(String basketHash, String itemHash, int itemQuantity) {
        List<BasketAndItemValidatorMessage> enumValidatorMessage = basketAndItemValidator.validate(basketHash, itemHash);
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
        List<BasketAndItemValidatorMessage> enumValidatorMessage = basketAndItemValidator.validate(basketHash, itemHash);
        List<String> validatorMessage = numberValidatorMessageConverter
                .convertValidatorMessageToString(enumValidatorMessage);
        if (basketAndItemValidator.isBasketAndItemAfterValidationAcceptable(enumValidatorMessage)) {
            BasketItem resultDeletion = basketItemDeleter.deleteBasketItem(basketHash, itemHash, validatorMessage);
            return new ServiceResultDto(validatorMessage, resultDeletion);
        }
//        System.out.println(basketItemRepository.findByHash(basketHash));
        return new ServiceResultDto(validatorMessage, null);
    }


    @Override
    public ServiceResultDto changeAmountOfProduct(String basketHash, String itemHash, int newQuantity) {
        List<BasketAndItemValidatorMessage> enumValidatorMessage = basketAndItemValidator.validate(basketHash, itemHash);
        List<String> validatorMessage = numberValidatorMessageConverter
                .convertValidatorMessageToString(enumValidatorMessage);
        if (basketAndItemValidator.isBasketAndItemAfterValidationAcceptable(enumValidatorMessage)) {
            BasketItem changerQuantityResult = basketItemQuantityChanger.changeQuantity(basketHash, itemHash, newQuantity, validatorMessage);
            return new ServiceResultDto(validatorMessage, changerQuantityResult);
        }
        return new ServiceResultDto(validatorMessage, null);
    }

//    @Override
//    public ServiceResultDto addDiscount(UUID basketHash, UUID itemHash, ) {
//        List<BasketAndItemValidatorMessage> enumValidatorMessage = validateMessage(basketHash, itemId);
//        List<String> validatorMessage = numberValidatorMessageConverter
//                .convertValidatorMessageToString(enumValidatorMessage);
//        if (basketAndItemValidator.isBasketAndItemAfterValidationAcceptable(enumValidatorMessage)) {
//            Basket currentBasket = basketItemRepository.findByBasketId(basketId).get();
//            Item productToChangeQuantity = itemRepository.findByProductId(itemId).get();
//            if (!basketAndItemValidator.isItemInBasket(currentBasket, productToChangeQuantity)) {
//                addMessageToValidatorMessage(validatorMessage, "This product is not in your basket");
//                System.out.println(basketItemRepository.findById(basketId));
//                return new ServiceResultDto(validatorMessage, null);
//            }
//            for (BasketItem item : currentBasket.getItemList()) {
//                if (item.getItem().equals(productToChangeQuantity)) {
//                    item.setQuantity(newQuantity);
//                    basketItemRepository.save(currentBasket);
//                    System.out.println(basketItemRepository.findById(basketId));
//                    return new ServiceResultDto(validatorMessage, item);
//                }
//            }
//        }
//        return new ServiceResultDto(validatorMessage, null);
//    }
}