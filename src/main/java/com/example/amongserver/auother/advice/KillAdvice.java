package com.example.amongserver.auother.advice;

import com.example.amongserver.auother.controller.restcontroller.UserRestController;
import com.example.amongserver.auother.dto.UserKillResponseDto;
import com.example.amongserver.auother.exception.UserAlreadyDeadException;
import com.example.amongserver.auother.exception.UserNotFoundException;
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
    public ResponseEntity<UserKillResponseDto> handlerUserAlreadyDeadException(UserAlreadyDeadException e) {
        log.error(e.getMessage(), e);
        UserKillResponseDto userKillResponseDto = new UserKillResponseDto(e.getMessage());
        return new ResponseEntity<>(userKillResponseDto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<UserKillResponseDto> handlerUserNotFoundException(UserNotFoundException e) {
        log.error(e.getMessage(), e);
        UserKillResponseDto userKillResponseDto = new UserKillResponseDto(e.getMessage());
        return new ResponseEntity<>(userKillResponseDto, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<UserKillResponseDto> handleDataAccessException(DataAccessException e) {
        log.error("Database error occurred", e);
        UserKillResponseDto response = new UserKillResponseDto("Database error occurred");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<UserKillResponseDto> handleGlobalException(Exception e) {
        UserKillResponseDto userKillResponseDto = new UserKillResponseDto(
                "Unexpected error occurred");
        log.error(userKillResponseDto.getErrorMessage() + " in UserRestController");
        return new ResponseEntity<>(userKillResponseDto,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
