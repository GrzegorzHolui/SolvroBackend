package com.solvro.solvrobackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Setter
@Getter
@EqualsAndHashCode
public class SummaryInfo {

    BigDecimal priceForProducts;
    DeliveryType deliveryType;
    List<DiscountCard> usedCard;
    BigDecimal finalPrice;
}
