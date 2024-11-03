package com.example.amongserver.room.exception;

public class UniqueSecretCodeNotGeneratedException extends RuntimeException {
    public UniqueSecretCodeNotGeneratedException() {
    }

    public UniqueSecretCodeNotGeneratedException(String message) {
        super(message);
    }
}
