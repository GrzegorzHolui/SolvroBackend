package com.solvro.solvrobackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Document(collection = "discountCard")
public class DiscountCard {
    @Id
    @EqualsAndHashCode.Exclude
    private UUID id;
    private  String cardHash;
    private  String discountProductName;
    TypeOfDiscount typeOfDiscount;
    BigDecimal value;

    public DiscountCard(String cardHash, String discountProductName, TypeOfDiscount typeOfDiscount, BigDecimal value) {
        this.id = UUID.randomUUID();
        this.cardHash = cardHash;
        this.discountProductName = discountProductName;
        this.typeOfDiscount = typeOfDiscount;
        this.value = value;
    }

}
