package com.solvro.solvrobackend.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@ToString
@Document(collection = "baskets")
public class Basket {
    @Id
    private UUID basketId;

    private List<BasketItem> itemList;

    public Basket() {
    }

    public Basket(List<BasketItem> itemList) {
        this.basketId = UUID.randomUUID();
        this.itemList = itemList;
    }

    public Basket(UUID basketId, List<BasketItem> itemList) {
        this.basketId = basketId;
        this.itemList = itemList;
    }

    public Optional<BasketItem> isProductInBasketItem(BasketItem basketItemToCheck) {
        return this.itemList.stream().filter((currenBasketItem) -> currenBasketItem.equals(basketItemToCheck)).findFirst();
    }
}
