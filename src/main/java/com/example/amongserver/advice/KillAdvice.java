package com.example.amongserver.advice;

import com.example.amongserver.controller.restcontroller.UserRestController;
import com.example.amongserver.dto.UserKillDtoResponse;
import com.example.amongserver.exception.UserAlreadyDeadException;
import com.example.amongserver.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = {UserRestController.class})
@Slf4j
public class KillAdvice {
    @ExceptionHandler(UserAlreadyDeadException.class)
    public ResponseEntity<UserKillDtoResponse> handlerUserAlreadyDeadException(UserAlreadyDeadException e) {
        log.error(e.getMessage(), e);
        UserKillDtoResponse userKillDtoResponse = new UserKillDtoResponse(e.getMessage());
        return new ResponseEntity<>(userKillDtoResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<UserKillDtoResponse> handlerUserNotFoundException(UserNotFoundException e) {
        log.error(e.getMessage(), e);
        UserKillDtoResponse userKillDtoResponse = new UserKillDtoResponse(e.getMessage());
        return new ResponseEntity<>(userKillDtoResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<UserKillDtoResponse> handleDataAccessException(DataAccessException e) {
        log.error("Database error occurred", e);
        UserKillDtoResponse response = new UserKillDtoResponse("Database error occurred");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<UserKillDtoResponse> handleGlobalException(Exception e) {
        UserKillDtoResponse userKillDtoResponse = new UserKillDtoResponse(
                "Unexpected error occurred");
        log.error(userKillDtoResponse.getErrorMassage() + " in UserRestController");
        return new ResponseEntity<>(userKillDtoResponse,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
