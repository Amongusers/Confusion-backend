package com.example.amongserver.auother.exception;

public class UserAlreadyDeadException extends RuntimeException{
    public UserAlreadyDeadException(String message) {
        super(message);
    }
    public UserAlreadyDeadException() {
        super();
    }
}
