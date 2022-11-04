package com.solvro.solvrobackend.dto;

import com.solvro.solvrobackend.model.DiscountCard;

import java.util.List;

public record ServiceDiscountCardResultDto(List<String> message, DiscountCard discountCard) {
}
