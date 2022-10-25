package com.solvro.solvrobackend.service;

import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
class ValidatorMessageConverter {
    List<String> convertValidatorMessageToString(List<BasketAndItemValidatorMessage> validatorMessages) {
        return validatorMessages.stream()
                .map(basketAndItemMessage -> basketAndItemMessage.message)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
