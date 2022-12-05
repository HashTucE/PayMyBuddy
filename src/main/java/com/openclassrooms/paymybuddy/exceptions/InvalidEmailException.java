package com.openclassrooms.paymybuddy.exceptions;

import com.openclassrooms.paymybuddy.configuration.Generated;

@Generated
public class InvalidEmailException extends RuntimeException {

    public InvalidEmailException(String errorMessage) {
        super(errorMessage);
    }


}