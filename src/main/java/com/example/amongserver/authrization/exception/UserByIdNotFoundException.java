package com.example.amongserver.authrization.exception;

public class UserByIdNotFoundException extends RuntimeException {

    public UserByIdNotFoundException(String message) {
        super(message);
    }
}
