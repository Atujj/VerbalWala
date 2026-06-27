package com.verbalwala.backend.exception;

public class AttemptNotFoundException extends RuntimeException {

    public AttemptNotFoundException(String message) {
        super(message);
    }

}