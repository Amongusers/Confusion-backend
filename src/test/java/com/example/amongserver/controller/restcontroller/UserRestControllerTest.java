package com.example.amongserver.controller.restcontroller;

import com.example.amongserver.advice.KillAdvice;
import com.example.amongserver.dto.UserKillDtoRequest;
import com.example.amongserver.dto.UserKillDtoResponse;
import com.example.amongserver.exception.UserAlreadyDeadException;
import com.example.amongserver.exception.UserNotFoundException;
import com.example.amongserver.service.UserGameDtoService;
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
class UserRestControllerTest {
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
        UserKillDtoRequest request = new UserKillDtoRequest(1L, 2L);

        mockMvc.perform(post("/user/kill")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(userGameDtoService, times(1)).killUser(request);
    }

    @Test
    void killUser_UserNotFound() throws Exception {
        UserKillDtoRequest request = new UserKillDtoRequest(1L, 2L);

        doThrow(new UserNotFoundException("User with id 1 not found"))
                .when(userGameDtoService)
                .killUser(request);

        mockMvc.perform(post("/user/kill")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        UserKillDtoResponse.builder().errorMassage("User with id 1 not found").build())));

        verify(userGameDtoService, times(1)).killUser(any(UserKillDtoRequest.class));
    }

    @Test
    void killUser_UserAlreadyDead() throws Exception {
        UserKillDtoRequest request = new UserKillDtoRequest(1L, 2L);

        doThrow(new UserAlreadyDeadException("User with id 1 already dead"))
                .when(userGameDtoService)
                .killUser(request);

        mockMvc.perform(post("/user/kill")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        UserKillDtoResponse.builder().errorMassage("User with id 1 already dead").build())));

        verify(userGameDtoService, times(1)).killUser(any(UserKillDtoRequest.class));
    }

    @Test
    void killUser_whenUnexpectedError_occurs() throws Exception {
        // Arrange
        Long idDead = 1L;
        Long idKiller = 2L;
        UserKillDtoRequest request = new UserKillDtoRequest(idKiller, idDead);

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
        UserKillDtoRequest request = new UserKillDtoRequest(1L, 2L);

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
