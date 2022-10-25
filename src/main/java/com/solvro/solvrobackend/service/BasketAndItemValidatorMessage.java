package com.solvro.solvrobackend.service;

enum BasketAndItemValidatorMessage {
    ITEM_NOT_EXIST("item_doesn't_exist"), BASKET_NOT_EXIST("basket_doesn't_exist"), EVERYTHING_IS_FINE("everything_is_fine");

    final String message;

    BasketAndItemValidatorMessage(String message) {
        this.message = message;
    }

    public boolean isEverythingFine() {
        return EVERYTHING_IS_FINE.equals(this);
    }
}