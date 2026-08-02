package com.alexander.openbanking_api.exception;

// thrown when a requested resource does not exist
public class ResourceNotFoundException
        extends RuntimeException {

    // constructor
    public ResourceNotFoundException(String message) {

        super(message);

    }

}