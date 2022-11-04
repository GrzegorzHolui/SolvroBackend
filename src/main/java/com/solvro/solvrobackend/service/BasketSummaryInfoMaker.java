package com.solvro.solvrobackend.service;

import com.solvro.solvrobackend.Repository.BasketRepository;
import com.solvro.solvrobackend.Repository.DiscountCardRepository;
import com.solvro.solvrobackend.Repository.ItemRepository;
import com.solvro.solvrobackend.model.Basket;
import com.solvro.solvrobackend.model.BasketItem;
import com.solvro.solvrobackend.model.DeliveryType;
import com.solvro.solvrobackend.model.DiscountCard;
import com.solvro.solvrobackend.model.SummaryInfo;
import com.solvro.solvrobackend.model.TypeOfDiscount;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class BasketSummaryInfoMaker {
    BasketRepository basketRepository;
    ItemRepository itemRepository;
    BasketAndItemValidator basketAndItemValidator;
    DiscountCardRepository discountCardRepository;

    public SummaryInfo generateSummaryInfo(String basketHash) {
        Basket currentBasket = basketRepository.findFirstByBasketHash(basketHash).get();
        BigDecimal priceForProduct = calculatePrice(currentBasket);
        List<DiscountCard> usedCard = currentBasket.getSummaryInfo().getUsedCard();
        BigDecimal finalPrice = calculateFinalPrice(currentBasket.getItemList(), usedCard, currentBasket.getSummaryInfo().getDeliveryType());
        return new SummaryInfo(priceForProduct, currentBasket.getSummaryInfo().getDeliveryType(), usedCard, finalPrice);
    }

    private static BigDecimal calculateFinalPrice(List<BasketItem> currentBasketItems, List<DiscountCard> usedCard, DeliveryType deliveryType) {
        Map<String, BigDecimal> itemsWithFinalSum = currentBasketItems.stream().collect(Collectors.toMap(key -> key.getItem().getNameOfProduct()
                , value -> BigDecimal.valueOf(value.getQuantity()).multiply(value.getItem().getPriceForOneItem()),
                (left, right) -> left, () -> new LinkedHashMap<>()
        ));
        BigDecimal result = BigDecimal.ZERO;
        if (deliveryType != null) {
            result = result.add(deliveryType.getDeliveryPrice());
        }
        for (String nameOfProduct : itemsWithFinalSum.keySet()) {
            for (DiscountCard discountCard : usedCard) {
                String discountProductName = discountCard.getDiscountProductName();
                TypeOfDiscount typeOfDiscount = discountCard.getTypeOfDiscount();
                BigDecimal currentValue = itemsWithFinalSum.get(nameOfProduct);
                if (nameOfProduct.equals(discountProductName) && typeOfDiscount.equals(TypeOfDiscount.CONSTANT)) {
                    BigDecimal newValueAfterDiscount = currentValue.subtract(discountCard.getValue());
                    result = result.add(newValueAfterDiscount);
                } else if (nameOfProduct.equals(discountProductName) && typeOfDiscount.equals(TypeOfDiscount.PERCENT)) {
                    BigDecimal newValueAfterDiscount = currentValue.subtract(currentValue.multiply(discountCard.getValue()));
                    result = result.add(newValueAfterDiscount);
                } else {
                    result = result.add(currentValue);
                }
            }
        }
        return result;
    }

    private static BigDecimal calculatePrice(Basket currentBasket) {
        return currentBasket.getItemList().stream()
                .map(basketItem -> BigDecimal.valueOf(basketItem.getQuantity())
                        .multiply(basketItem.getItem().getPriceForOneItem()))
                .reduce((previousPrice, currentPrice) -> previousPrice.add(currentPrice))
                .orElseThrow();
    }
}