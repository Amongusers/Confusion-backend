package com.example.amongserver.authorization.exception;

public class UserByIdNotFoundException extends RuntimeException {

    public UserByIdNotFoundException(String message) {
        super(message);
    }
}
