package com.solvro.solvrobackend.model;

import lombok.AllArgsConstructor;
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
@ToString
@Setter
@Getter
@EqualsAndHashCode
public class SummaryInfo {

    BigDecimal priceForProducts;
    List<DiscountCard> usedCard = new ArrayList<>();
    BigDecimal finalPrice;
}
