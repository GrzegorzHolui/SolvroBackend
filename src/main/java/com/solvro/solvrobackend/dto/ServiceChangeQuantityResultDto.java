package com.solvro.solvrobackend.dto;

import java.util.List;

public record ServiceChangeQuantityResultDto(List<String> message, Integer quantity) {
}
