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
@Document(collection = "discountCard")
public class DiscountCard {
    @Id
    private UUID id;
    private final String cardHash;

    private final String discountProductName;
    TypeOfDiscount typeOfDiscount;
    BigDecimal value;

    public DiscountCard(UUID id, String cardHash, String discountProductName, TypeOfDiscount typeOfDiscount, BigDecimal value) {
        this.id = UUID.randomUUID();
        this.cardHash = cardHash;
        this.discountProductName = discountProductName;
        this.typeOfDiscount = typeOfDiscount;
        this.value = value;

    }
}
