package com.openclassrooms.paymybuddy.exceptions;

import com.openclassrooms.paymybuddy.configuration.Generated;

@Generated
public class AlreadyBuddyException extends RuntimeException {

    public AlreadyBuddyException(String errorMessage) {
        super(errorMessage);
    }


}
