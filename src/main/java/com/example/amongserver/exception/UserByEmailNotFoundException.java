package com.example.amongserver.exception;

public class UserByEmailNotFoundException extends RuntimeException {
    public UserByEmailNotFoundException(String message) {
        super(message);
    }
}
