package com.solvro.solvrobackend.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
class ServiceResultExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(ServiceResultException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String InputNumbersErrorHandler(ServiceResultException InputNumbersException) {
        return InputNumbersException.getMessage();
    }
}