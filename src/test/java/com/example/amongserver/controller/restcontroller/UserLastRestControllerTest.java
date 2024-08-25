package com.example.amongserver.controller.restcontroller;

import com.example.amongserver.auother.advice.KillAdvice;
import com.example.amongserver.auother.controller.restcontroller.UserRestController;
import com.example.amongserver.auother.dto.UserKillRequestDto;
import com.example.amongserver.auother.dto.UserKillResponseDto;
import com.example.amongserver.auother.exception.UserAlreadyDeadException;
import com.example.amongserver.auother.exception.UserNotFoundException;
import com.example.amongserver.auother.service.UserGameDtoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserLastRestControllerTest {
    @Mock
    private UserGameDtoService userGameDtoService;

    @InjectMocks
    private UserRestController userRestController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userRestController)
                .setControllerAdvice(new KillAdvice())  // Настройка глобального обработчика исключений
                .build();
        objectMapper = new ObjectMapper();  // Для сериализации/десериализации JSON
    }

    @Test
    void killUser_Success() throws Exception {
        UserKillRequestDto request = new UserKillRequestDto(1L, 2L);

        mockMvc.perform(post("/user/kill")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(userGameDtoService, times(1)).killUser(request);
    }

    @Test
    void killUser_UserNotFound() throws Exception {
        UserKillRequestDto request = new UserKillRequestDto(1L, 2L);

        doThrow(new UserNotFoundException("User with id 1 not found"))
                .when(userGameDtoService)
                .killUser(request);

        mockMvc.perform(post("/user/kill")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        UserKillResponseDto.builder().errorMessage("User with id 1 not found").build())));

        verify(userGameDtoService, times(1)).killUser(any(UserKillRequestDto.class));
    }

    @Test
    void killUser_UserAlreadyDead() throws Exception {
        UserKillRequestDto request = new UserKillRequestDto(1L, 2L);

        doThrow(new UserAlreadyDeadException("User with id 1 already dead"))
                .when(userGameDtoService)
                .killUser(request);

        mockMvc.perform(post("/user/kill")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        UserKillResponseDto.builder().errorMessage("User with id 1 already dead").build())));

        verify(userGameDtoService, times(1)).killUser(any(UserKillRequestDto.class));
    }

    @Test
    void killUser_whenUnexpectedError_occurs() throws Exception {
        // Arrange
        Long idDead = 1L;
        Long idKiller = 2L;
        UserKillRequestDto request = new UserKillRequestDto(idKiller, idDead);

        // Настройка поведения mock
        doThrow(new RuntimeException("Unexpected error")).when(userGameDtoService).killUser(request);

        // Act & Assert
        mockMvc.perform(post("/user/kill")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idKiller\":2,\"idDead\":1}"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.errorMassage").value("Unexpected error occurred"));
    }
    @Test
    void killUser_whenDataAccessExceptionThrown_returnsInternalServerError() throws Exception {
        // Arrange
        UserKillRequestDto request = new UserKillRequestDto(1L, 2L);

        // Имитация выброса DataAccessException в сервисе
        doThrow(new DataAccessException("Simulated database error") {}).when(userGameDtoService).killUser(request);

        // Act & Assert
        mockMvc.perform(post("/user/kill")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.errorMassage").value("Database error occurred"));

        // Проверка, что метод был вызван
        verify(userGameDtoService, times(1)).killUser(any());
    }
}
