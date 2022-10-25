package com.solvro.solvrobackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@ToString
@AllArgsConstructor
@Document(collection = "basketItem")
public class BasketItem {
    Item item;
    @EqualsAndHashCode.Exclude
    int quantity;

}
