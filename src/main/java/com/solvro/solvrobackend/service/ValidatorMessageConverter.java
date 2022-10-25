package service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class ValidatorMessageConverter {
    List<String> convertValidatorMessageToString(List<BasketAndItemValidatorMessage> validatorMessages) {
        return validatorMessages.stream()
                .map(basketAndItemMessage -> basketAndItemMessage.message)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
