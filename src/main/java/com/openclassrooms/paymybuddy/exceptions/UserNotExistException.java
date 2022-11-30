package com.openclassrooms.paymybuddy.exceptions;

public class UserNotExistException extends RuntimeException {

    public UserNotExistException(String errorMessage) {
        super(errorMessage);
    }


}