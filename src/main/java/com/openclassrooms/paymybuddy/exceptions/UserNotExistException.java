package com.openclassrooms.paymybuddy.exceptions;

import com.openclassrooms.paymybuddy.configuration.Generated;

@Generated
public class UserNotExistException extends RuntimeException {

    public UserNotExistException(String errorMessage) {
        super(errorMessage);
    }


}