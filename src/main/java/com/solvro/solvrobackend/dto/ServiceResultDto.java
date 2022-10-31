package com.solvro.solvrobackend.dto;

import java.util.List;

public record ServiceResultDto(List<String> message, Object resultOfMethod) {
}
