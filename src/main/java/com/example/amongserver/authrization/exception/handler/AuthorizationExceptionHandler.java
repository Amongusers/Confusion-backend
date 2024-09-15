package com.example.amongserver.authrization.exception.handler;

import com.example.amongserver.authrization.contoller.UserAuthController;
import com.example.amongserver.authrization.dto.UserAuthErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = {UserAuthController.class})
@Slf4j
public class AuthorizationExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<UserAuthErrorResponseDto> handleBadCredentialsException(BadCredentialsException ex) {
        log.error("Authentication failed: {}", ex.getMessage());

        // Создаем объект с сообщением об ошибке
        UserAuthErrorResponseDto errorResponse = UserAuthErrorResponseDto.builder()
                .errorMessage("Invalid email or password")
                .build();

        // Возвращаем ResponseEntity с телом ошибки и статусом 401 (Unauthorized)
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}
