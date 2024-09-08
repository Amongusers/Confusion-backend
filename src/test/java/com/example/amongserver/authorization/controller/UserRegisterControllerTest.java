package com.example.amongserver.authorization.controller;

import com.example.amongserver.authorization.dto.UserRegisterRequestDto;
import com.example.amongserver.authorization.exception.AuthorityNotFoundException;
import com.example.amongserver.authorization.exception.UserAlreadyExistsException;
import com.example.amongserver.authorization.exception.UserByEmailNotFoundException;
import com.example.amongserver.authorization.service.UserRegisterService;
import com.example.amongserver.jwt.manager.JwtTokenManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.containsString;

@WebMvcTest(UserRegisterController.class)
public class UserRegisterControllerTest {
    // TODO : из-за не настроенной конфигурации тесты не проходят

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRegisterService userRegisterService;

    @MockBean
    private JwtTokenManager jwtTokenManager;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldRegisterUserSuccessfully() throws Exception {
        // Arrange
        UserRegisterRequestDto requestDto = UserRegisterRequestDto.builder()
                .username("testUser")
                .email("test@example.com")
                .password("password123")
                .build();

        // Act & Assert
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated());

        verify(userRegisterService, times(1)).saveUser(any(UserRegisterRequestDto.class));
    }

    @Test
    void shouldReturnConflictWhenUserAlreadyExists() throws Exception {
        // Arrange
        UserRegisterRequestDto requestDto = UserRegisterRequestDto.builder()
                .username("testUser")
                .email("test@example.com")
                .password("password123")
                .build();

        doThrow(new UserAlreadyExistsException("User with email test@example.com already exists"))
                .when(userRegisterService).saveUser(any(UserRegisterRequestDto.class));

        // Act & Assert
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorMessage", containsString("User with email test@example.com already exists")));
    }

    @Test
    void shouldReturnBadRequestWhenRoleNotFound() throws Exception {
        // Arrange
        UserRegisterRequestDto requestDto = UserRegisterRequestDto.builder()
                .username("testUser")
                .email("test@example.com")
                .password("password123")
                .build();

        doThrow(new AuthorityNotFoundException("Authority not found!"))
                .when(userRegisterService).saveUser(any(UserRegisterRequestDto.class));

        // Act & Assert
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage", containsString("Authority not found!")));
    }

    @Test
    void shouldReturnNotFoundWhenUserByEmailNotFound() throws Exception {
        // Arrange
        UserRegisterRequestDto requestDto = UserRegisterRequestDto.builder()
                .username("testUser")
                .email("test@example.com")
                .password("password123")
                .build();

        doThrow(new UserByEmailNotFoundException("User not found!"))
                .when(userRegisterService).saveUser(any(UserRegisterRequestDto.class));

        // Act & Assert
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage", containsString("User not found!")));
    }


    @Test
    void shouldReturnBadRequestWhenValidationFails() throws Exception {
        // Arrange
        UserRegisterRequestDto requestDto = UserRegisterRequestDto.builder()
                .username("") // Некорректное значение
                .email("invalidEmail") // Некорректный email
                .password("pass") // Слишком короткий пароль
                .build();

        // Act & Assert
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage", containsString("Username is mandatory")))
                .andExpect(jsonPath("$.errorMessage", containsString("Email should be valid")))
                .andExpect(jsonPath("$.errorMessage", containsString("Password must be at least 6 characters long")));
    }




}
