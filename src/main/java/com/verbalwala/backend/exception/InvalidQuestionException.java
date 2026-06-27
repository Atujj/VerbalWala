package com.verbalwala.backend.exception;

public class InvalidQuestionException extends RuntimeException {

    public InvalidQuestionException(String message) {
        super(message);
    }

}