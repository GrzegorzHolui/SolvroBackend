package com.solvro.solvrobackend.exceptions;

public class ServiceResultException extends RuntimeException {
    public ServiceResultException(String textError) {
        super(textError);
    }
}
