package com.example.amongserver.room.controller.exception;

public class UserInContentNotFoundException extends RuntimeException {
    public UserInContentNotFoundException() {
    }

    public UserInContentNotFoundException(String message) {
        super(message);
    }
}
