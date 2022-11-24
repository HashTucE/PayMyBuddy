package com.openclassrooms.paymybuddy.exceptions;


public class NoBankAccountException extends RuntimeException {

    public NoBankAccountException(String errorMessage) {
        super(errorMessage);
    }


}