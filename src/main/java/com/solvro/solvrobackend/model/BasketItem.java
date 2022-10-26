package com.solvro.solvrobackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@ToString
@AllArgsConstructor

public class BasketItem {
    Item item;
    @EqualsAndHashCode.Exclude
    int quantity;

}
