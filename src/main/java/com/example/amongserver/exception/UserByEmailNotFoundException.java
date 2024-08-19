package com.example.amongserver.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserByEmailNotFoundException extends UsernameNotFoundException {
    public UserByEmailNotFoundException(String message) {
        super(message);
    }
}
