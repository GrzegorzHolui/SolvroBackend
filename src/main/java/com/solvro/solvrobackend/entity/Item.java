package com.solvro.solvrobackend.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.UUID;


@Getter
@EqualsAndHashCode
@Builder
@ToString
@Document(collection = "item")
public class Item {

    @Id
    private UUID productId;

    public Item() {
    }

    private String nameOfProduct;
    private BigDecimal price;

    public Item(String nameOfProduct, BigDecimal price) {
        this.productId = UUID.randomUUID();
        this.nameOfProduct = nameOfProduct;
        this.price = price;
    }

    public Item(UUID productId, String nameOfProduct, BigDecimal price) {
        this.productId = productId;
        this.nameOfProduct = nameOfProduct;
        this.price = price;
    }
}
