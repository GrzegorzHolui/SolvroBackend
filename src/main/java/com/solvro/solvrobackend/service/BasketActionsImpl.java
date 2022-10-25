package service;

import Repository.BasketRepository;
import Repository.ItemRepository;
import entity.Basket;
import entity.BasketItem;
import entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.dto.ServiceResultDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static service.BasketAndItemValidatorMessage.EVERYTHING_IS_FINE;

@Service
public class BasketActionsImpl implements BasketActions {

    BasketRepository basketItemRepository;
    ItemRepository itemRepository;
    BasketAndItemValidator basketAndItemValidator;
    ValidatorMessageConverter numberValidatorMessageConverter;

    private static final int FIRST_INDEX = 0;

    @Autowired
    public BasketActionsImpl(BasketRepository basketItemRepository, ItemRepository itemRepository, BasketAndItemValidator basketAndItemValidator, ValidatorMessageConverter numberValidatorMessageConverter) {
        this.basketItemRepository = basketItemRepository;
        this.itemRepository = itemRepository;
        this.basketAndItemValidator = basketAndItemValidator;
        this.numberValidatorMessageConverter = numberValidatorMessageConverter;
    }

    @Override
    public ServiceResultDto addItem(UUID basketId, UUID itemId, int itemQuantity) {
        List<BasketAndItemValidatorMessage> enumValidatorMessage = basketAndItemValidator.validate(basketId, itemId);
        List<String> validatorMessage = numberValidatorMessageConverter
                .convertValidatorMessageToString(enumValidatorMessage);
        if (enumValidatorMessage.get(FIRST_INDEX).equals(EVERYTHING_IS_FINE)) {
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
                return new ServiceResultDto(validatorMessage, currentBasketItem);
            } else {
                int currentQuantity = productInBasketItem.get().getQuantity();
                productInBasketItem.get().setQuantity(currentQuantity + itemQuantity);
                return new ServiceResultDto(validatorMessage, productInBasketItem.get());
            }
        }
        return new ServiceResultDto(validatorMessage, null);
    }

    @Override
    public ServiceResultDto deleteItem(UUID basketId, UUID itemId) {
        List<BasketAndItemValidatorMessage> enumValidatorMessage = basketAndItemValidator.validate(basketId, itemId);
        List<String> validatorMessage = numberValidatorMessageConverter
                .convertValidatorMessageToString(enumValidatorMessage);
        if (enumValidatorMessage.get(FIRST_INDEX).equals(EVERYTHING_IS_FINE)) {
            Basket currentBasket = basketItemRepository.findByBasketId(basketId).get();
            Item productToRemove = itemRepository.findByProductId(itemId).get();
            if (!basketAndItemValidator.isItemInBasket(currentBasket, productToRemove)) {
                addMessageToValidatorMessage(validatorMessage);
                return new ServiceResultDto(validatorMessage, null);
            }
            for (BasketItem item : currentBasket.getItemList()) {
                if (item.getItem().equals(productToRemove)) {
                    currentBasket.getItemList().remove(item);
                    return new ServiceResultDto(validatorMessage, item);
                }
            }
        }
        return new ServiceResultDto(validatorMessage, null);
    }

    private void addMessageToValidatorMessage(List<String> validatorMessage) {
        validatorMessage.removeAll(validatorMessage);
        validatorMessage.add("This product is not in your basket");
    }


}