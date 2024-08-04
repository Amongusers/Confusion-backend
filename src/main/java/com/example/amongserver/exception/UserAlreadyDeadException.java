package com.example.amongserver.exception;

public class UserAlreadyDeadException extends RuntimeException{
    public UserAlreadyDeadException(String message) {
        super(message);
    }
    public UserAlreadyDeadException() {
        super();
    }
}
