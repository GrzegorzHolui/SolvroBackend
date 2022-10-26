package com.solvro.solvrobackend.controllers.RemoverBasket.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class ItemAdderExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(ItemAdderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String InputNumbersErrorHandler(ItemAdderException InputNumbersException) {
        return InputNumbersException.getMessage();
    }
}