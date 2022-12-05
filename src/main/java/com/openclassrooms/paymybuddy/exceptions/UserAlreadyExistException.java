package com.openclassrooms.paymybuddy.exceptions;

import com.openclassrooms.paymybuddy.configuration.Generated;

@Generated
public class UserAlreadyExistException extends RuntimeException {

    public UserAlreadyExistException(String errorMessage) {
        super(errorMessage);
    }


}
