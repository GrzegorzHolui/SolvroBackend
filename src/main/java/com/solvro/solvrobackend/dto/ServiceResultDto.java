package com.solvro.solvrobackend.service.dto;

import com.solvro.solvrobackend.entity.BasketItem;

import java.util.List;

public record ServiceResultDto(List<String> message, BasketItem basketItem) {
}
