package com.solvro.solvrobackend.controllers.adderContoller.exceptions;

public class ItemAdderException extends RuntimeException {
    public ItemAdderException(String textError) {
        super(textError);
        //TODO MAYBE change ItemAdderException to ControllerException
    }
}
