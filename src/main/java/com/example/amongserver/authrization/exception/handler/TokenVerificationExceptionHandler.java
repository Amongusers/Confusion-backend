package com.example.amongserver.authrization.exception.handler;

import com.example.amongserver.authrization.dto.UserAuthErrorResponseDto;
import com.example.amongserver.authrization.exception.UserByIdNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(annotations = JwtTokenExceptionHandler.class)
@Slf4j
public class TokenVerificationExceptionHandler {

    @ExceptionHandler(UserByIdNotFoundException.class)
    public ResponseEntity<UserAuthErrorResponseDto> handleUserByIdNotFoundException(UserByIdNotFoundException ex) {
        log.error("Token verification error: {}", ex.getMessage());
        UserAuthErrorResponseDto response = UserAuthErrorResponseDto.builder()
                .errorMessage(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
