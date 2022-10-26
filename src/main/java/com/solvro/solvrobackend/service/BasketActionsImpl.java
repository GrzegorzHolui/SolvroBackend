package com.solvro.solvrobackend.service;

import com.solvro.solvrobackend.Repository.BasketRepository;
import com.solvro.solvrobackend.Repository.ItemRepository;
import com.solvro.solvrobackend.model.Basket;
import com.solvro.solvrobackend.model.BasketItem;
import com.solvro.solvrobackend.model.Item;
import com.solvro.solvrobackend.dto.ServiceResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BasketActionsImpl implements BasketActions {

    BasketRepository basketItemRepository;
    ItemRepository itemRepository;
    BasketAndItemValidator basketAndItemValidator;
    ValidatorMessageConverter numberValidatorMessageConverter;

    private static final int FIRST_INDEX = 0;


    @EventListener(ApplicationReadyEvent.class)
    public void fillDB() {
        Item laptop = itemRepository.save(new Item(UUID.fromString("ba0f8c84-d37c-4521-9938-dfd14bc99757"), "laptop", BigDecimal.ONE));
        basketItemRepository.save(new Basket(UUID.fromString("dea38381-7ab0-4131-b1f5-23359ca2b834"), new ArrayList<>()));
    }


    @Autowired
    public BasketActionsImpl(BasketRepository basketItemRepository, ItemRepository itemRepository, BasketAndItemValidator basketAndItemValidator, ValidatorMessageConverter numberValidatorMessageConverter) {
        this.basketItemRepository = basketItemRepository;
        this.itemRepository = itemRepository;
        this.basketAndItemValidator = basketAndItemValidator;
        this.numberValidatorMessageConverter = numberValidatorMessageConverter;
    }

    @Override
    public ServiceResultDto addItem(UUID basketId, UUID itemId, int itemQuantity) {
        List<BasketAndItemValidatorMessage> enumValidatorMessage = validateMessage(basketId, itemId);
        List<String> validatorMessage = numberValidatorMessageConverter
                .convertValidatorMessageToString(enumValidatorMessage);
        if (basketAndItemValidator.isBasketAndItemAfterValidationAcceptable(enumValidatorMessage)) {
            Basket currentBasket = basketItemRepository.findByBasketId(basketId).get();
            Item currentProduct = itemRepository.findByProductId(itemId).get();
            BasketItem currentBasketItem = BasketItem.builder()
                    .item(currentProduct)
                    .quantity(itemQuantity)
                    .build();
            Optional<BasketItem> productInBasketItem = currentBasket.isProductInBasketItem(currentBasketItem);
            if (productInBasketItem.isEmpty()) {
                currentBasket.getItemList().add(currentBasketItem);
                basketItemRepository.save(currentBasket);
//                System.out.println(basketItemRepository.findById(basketId));
                return new ServiceResultDto(validatorMessage, currentBasketItem);
            } else {
                int currentQuantity = productInBasketItem.get().getQuantity();
                productInBasketItem.get().setQuantity(currentQuantity + itemQuantity);
                basketItemRepository.save(currentBasket);
//                System.out.println(basketItemRepository.findById(basketId));
                return new ServiceResultDto(validatorMessage, productInBasketItem.get());
            }
        }
//        System.out.println(basketItemRepository.findById(basketId));
        return new ServiceResultDto(validatorMessage, null);
    }

    @Override
    public ServiceResultDto deleteItem(UUID basketId, UUID itemId) {
        List<BasketAndItemValidatorMessage> enumValidatorMessage = validateMessage(basketId, itemId);
        List<String> validatorMessage = numberValidatorMessageConverter
                .convertValidatorMessageToString(enumValidatorMessage);
        if (basketAndItemValidator.isBasketAndItemAfterValidationAcceptable(enumValidatorMessage)) {
            Basket currentBasket = basketItemRepository.findByBasketId(basketId).get();
            Item productToRemove = itemRepository.findByProductId(itemId).get();
            if (!basketAndItemValidator.isItemInBasket(currentBasket, productToRemove)) {
                addMessageToValidatorMessage(validatorMessage, "This product is not in your basket");
//                System.out.println(basketItemRepository.findById(basketId));
                return new ServiceResultDto(validatorMessage, null);
            }
            for (BasketItem item : currentBasket.getItemList()) {
                if (item.getItem().equals(productToRemove)) {
                    currentBasket.getItemList().remove(item);
                    basketItemRepository.save(currentBasket);
//                    System.out.println(basketItemRepository.findById(basketId));
                    return new ServiceResultDto(validatorMessage, item);
                }
            }
        }
//        System.out.println(basketItemRepository.findById(basketId));
        return new ServiceResultDto(validatorMessage, null);
    }

    private List<BasketAndItemValidatorMessage> validateMessage(UUID basketId, UUID itemId) {
        return basketAndItemValidator.validate(basketId, itemId);
    }

    @Override
    public ServiceResultDto changeAmountOfProduct(UUID basketId, UUID itemId, int newQuantity) {
        List<BasketAndItemValidatorMessage> enumValidatorMessage = validateMessage(basketId, itemId);
        List<String> validatorMessage = numberValidatorMessageConverter
                .convertValidatorMessageToString(enumValidatorMessage);
        if (basketAndItemValidator.isBasketAndItemAfterValidationAcceptable(enumValidatorMessage)) {
            Basket currentBasket = basketItemRepository.findByBasketId(basketId).get();
            Item productToChangeQuantity = itemRepository.findByProductId(itemId).get();
            if (!basketAndItemValidator.isItemInBasket(currentBasket, productToChangeQuantity)) {
                addMessageToValidatorMessage(validatorMessage, "This product is not in your basket");
                System.out.println(basketItemRepository.findById(basketId));
                return new ServiceResultDto(validatorMessage, null);
            }
            for (BasketItem item : currentBasket.getItemList()) {
                if (item.getItem().equals(productToChangeQuantity)) {
                    item.setQuantity(newQuantity);
                    basketItemRepository.save(currentBasket);
                    System.out.println(basketItemRepository.findById(basketId));
                    return new ServiceResultDto(validatorMessage, item);
                }
            }
        }
        return new ServiceResultDto(validatorMessage, null);
    }

//    @Override
//    public ServiceResultDto addDiscount(UUID basketId, UUID itemId, ) {
//        List<BasketAndItemValidatorMessage> enumValidatorMessage = validateMessage(basketId, itemId);
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


    private void addMessageToValidatorMessage(List<String> validatorMessage, String message) {
        validatorMessage.removeAll(validatorMessage);
        validatorMessage.add(message);
    }


}