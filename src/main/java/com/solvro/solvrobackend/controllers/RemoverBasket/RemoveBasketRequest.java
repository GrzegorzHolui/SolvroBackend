package com.solvro.solvrobackend.controllers.RemoverBasket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class RemoveBasketRequest {
    UUID basketId;
    UUID itemId;
}
