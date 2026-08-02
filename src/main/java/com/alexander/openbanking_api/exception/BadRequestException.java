package com.alexander.openbanking_api.exception;

// thrown when the client sends an invalid request
public class BadRequestException
        extends RuntimeException {

    // constructor
    public BadRequestException(String message) {

        super(message);

    }

}