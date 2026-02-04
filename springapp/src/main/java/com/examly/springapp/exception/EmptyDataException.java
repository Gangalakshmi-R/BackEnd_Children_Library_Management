package com.examly.springapp.exception;

public class EmptyDataException extends RuntimeException {

    public EmptyDataException(String message) {
        super(message);
    }
}
