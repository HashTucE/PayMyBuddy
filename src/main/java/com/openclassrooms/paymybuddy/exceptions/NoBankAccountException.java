package com.openclassrooms.paymybuddy.exceptions;

import com.openclassrooms.paymybuddy.configuration.Generated;

@Generated
public class NoBankAccountException extends RuntimeException {

    public NoBankAccountException(String errorMessage) {
        super(errorMessage);
    }


}