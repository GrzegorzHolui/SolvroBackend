package com.solvro.solvrobackend.controllers.exceptions;

public class ServiceResultException extends RuntimeException {
    public ServiceResultException(String textError) {
        super(textError);
    }
}
