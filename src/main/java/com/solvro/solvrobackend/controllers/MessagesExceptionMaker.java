package com.solvro.solvrobackend.controllers;

import java.util.List;


class MessagesExceptionMaker {
    public static String makeMessage(List<String> exceptionMessage) {
        return exceptionMessage.stream()
                .reduce((currentText, nextText) -> currentText + " , " + nextText)
                .orElse("sth was wrong");
    }
}
