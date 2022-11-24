package com.openclassrooms.paymybuddy.exceptions;

public class InvalidEmailException extends RuntimeException {

    public InvalidEmailException(String errorMessage) {
        super(errorMessage);
    }


}