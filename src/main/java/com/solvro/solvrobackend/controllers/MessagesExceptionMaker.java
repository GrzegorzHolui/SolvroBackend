package com.solvro.solvrobackend.controllers;

import com.solvro.solvrobackend.dto.ServiceResultDto;

public class MessagesExceptionMaker {
    public static String makeMessage(ServiceResultDto serviceResultDto) {
        return serviceResultDto.message().stream()
                .reduce((currentText, nextText) -> currentText + " , " + nextText)
                .orElse("sth got wrong please contact with man who made this app");
    }
}
