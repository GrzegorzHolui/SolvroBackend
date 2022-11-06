package com.solvro.solvrobackend.service;

enum BasketAndItemValidatorMessage {
    ITEM_NOT_EXIST("item doesn't exist"), BASKET_NOT_EXIST("basket doesn't exist"), EVERYTHING_IS_FINE("everything is fine");

    final String message;
    BasketAndItemValidatorMessage(String message) {
        this.message = message;
    }
}