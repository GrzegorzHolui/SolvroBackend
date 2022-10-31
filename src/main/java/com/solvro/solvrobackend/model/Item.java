package com.solvro.solvrobackend.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.UUID;


@Getter
@EqualsAndHashCode
@ToString
@Document(collection = "item")
public class Item {
    @Id
    private UUID productId;

    private String productHash;

    public Item() {
    }

    private String nameOfProduct;
    private BigDecimal priceForOneItem;

    public Item(String nameOfProduct, BigDecimal priceForOneItem, String productHash) {
        this.productId = UUID.randomUUID();
        this.nameOfProduct = nameOfProduct;
        this.priceForOneItem = priceForOneItem;
        this.productHash = productHash;
    }

    public Item(UUID productId, String nameOfProduct, BigDecimal priceForOneItem, String productHash) {
        this.productId = productId;
        this.nameOfProduct = nameOfProduct;
        this.priceForOneItem = priceForOneItem;
        this.productHash = productHash;
    }
}
