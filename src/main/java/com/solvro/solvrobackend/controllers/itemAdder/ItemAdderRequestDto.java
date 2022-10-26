package com.solvro.solvrobackend.controllers.itemAdder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ItemAdderRequestDto {
    String basketHash;
    String itemHash;
    int itemQuantity;
}
