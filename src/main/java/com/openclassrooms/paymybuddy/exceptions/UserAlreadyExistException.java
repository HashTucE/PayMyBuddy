package com.openclassrooms.paymybuddy.exceptions;

public class UserAlreadyExistException extends RuntimeException {

    public UserAlreadyExistException(String errorMessage) {
        super(errorMessage);
    }


}
