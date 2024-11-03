package com.example.amongserver.room.exception;

public class UserInContentNotFoundException extends RuntimeException {
    public UserInContentNotFoundException() {
    }

    public UserInContentNotFoundException(String message) {
        super(message);
    }
}
