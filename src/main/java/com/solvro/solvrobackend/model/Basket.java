package com.solvro.solvrobackend.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Builder
@ToString
@Document(collection = "baskets")
public class Basket {
    @Id
    private UUID basketId;
    private String basketHash;
    private List<BasketItem> itemList;

    public Basket(List<BasketItem> itemList, String basketHash) {
        this.basketId = UUID.randomUUID();
        this.itemList = itemList;
        this.basketHash = basketHash;
    }

    public Basket(UUID basketId, String basketHash, List<BasketItem> itemList) {
        this.basketId = basketId;
        this.basketHash = basketHash;
        this.itemList = itemList;
    }


    public Optional<BasketItem> isProductInBasketItem(BasketItem basketItemToCheck) {
        return this.itemList.stream().filter((currenBasketItem) -> currenBasketItem.equals(basketItemToCheck)).findFirst();
    }
}
