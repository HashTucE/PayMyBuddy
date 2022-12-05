package com.openclassrooms.paymybuddy.exceptions;

import com.openclassrooms.paymybuddy.configuration.Generated;

@Generated
public class InsufficientFundException extends RuntimeException {

    public InsufficientFundException(String errorMessage) {
        super(errorMessage);
    }


}
