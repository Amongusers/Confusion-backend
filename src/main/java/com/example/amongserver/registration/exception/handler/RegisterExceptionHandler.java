package com.example.amongserver.registration.exception.handler;

import com.example.amongserver.registration.controller.UserRegisterController;
import com.example.amongserver.registration.controller.dto.UserRegisterErrorResponseDto;
import com.example.amongserver.registration.exception.AuthorityNotFoundException;
import com.example.amongserver.registration.exception.UserAlreadyExistsException;
import com.example.amongserver.registration.exception.UserByEmailNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.context.support.DefaultMessageSourceResolvable;


import java.util.stream.Collectors;

@ControllerAdvice(assignableTypes = {UserRegisterController.class})
@Slf4j
public class RegisterExceptionHandler {
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<UserRegisterErrorResponseDto> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        log.error("Registration error: {}", ex.getMessage());
        UserRegisterErrorResponseDto response = UserRegisterErrorResponseDto.builder()
                .errorMessage(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // TODO : статус для Authority
    @ExceptionHandler(AuthorityNotFoundException.class)
    public ResponseEntity<UserRegisterErrorResponseDto> handleAuthorityNotFoundException(AuthorityNotFoundException ex) {
        log.error("Registration error: {}", ex.getMessage());
        UserRegisterErrorResponseDto response = UserRegisterErrorResponseDto.builder()
                .errorMessage(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserByEmailNotFoundException.class)
    public ResponseEntity<UserRegisterErrorResponseDto> handleUserByEmailNotFoundException(UserByEmailNotFoundException ex) {
        log.error("User not found error: {}", ex.getMessage());
        UserRegisterErrorResponseDto response = UserRegisterErrorResponseDto.builder()
                .errorMessage(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<UserRegisterErrorResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Validation error: {}", ex.getMessage());


        String errorMessage = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        UserRegisterErrorResponseDto response = UserRegisterErrorResponseDto.builder()
                .errorMessage(errorMessage)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<UserRegisterErrorResponseDto> handleGeneralException(Exception ex) {
        log.error("An unexpected error occurred: {}", ex.getMessage());
        UserRegisterErrorResponseDto response = UserRegisterErrorResponseDto.builder()
                .errorMessage("An unexpected error occurred: " + ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
