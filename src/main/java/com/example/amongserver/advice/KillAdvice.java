package com.example.amongserver.advice;

import com.example.amongserver.controller.restcontroller.UserRestController;
import com.example.amongserver.dto.UserKillDtoResponse;
import com.example.amongserver.exception.UserAlreadyDeadException;
import com.example.amongserver.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = {UserRestController.class})
@Slf4j
public class KillAdvice {
    // TODO: нужно проработать коды статусов ответа
    @ExceptionHandler(UserAlreadyDeadException.class)
    public ResponseEntity<UserKillDtoResponse> handlerUserAlreadyDeadException(UserAlreadyDeadException e) {
        log.error(e.getMessage(), e);
        UserKillDtoResponse userKillDtoResponse = new UserKillDtoResponse(e.getMessage());
        return new ResponseEntity<>(userKillDtoResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<UserKillDtoResponse> handlerUserNotFoundException(UserNotFoundException e) {
        log.error(e.getMessage(), e);
        UserKillDtoResponse userKillDtoResponse = new UserKillDtoResponse(e.getMessage());
        return new ResponseEntity<>(userKillDtoResponse, HttpStatus.NOT_FOUND);
    }
}
