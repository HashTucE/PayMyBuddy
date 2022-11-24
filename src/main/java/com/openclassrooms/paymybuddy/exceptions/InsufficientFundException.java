package com.openclassrooms.paymybuddy.exceptions;


public class InsufficientFundException extends RuntimeException {

    public InsufficientFundException(String errorMessage) {
        super(errorMessage);
    }


}
