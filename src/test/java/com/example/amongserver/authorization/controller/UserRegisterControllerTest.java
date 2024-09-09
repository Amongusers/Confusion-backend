package com.example.amongserver.authorization.controller;

import com.example.amongserver.authorization.dto.UserRegisterRequestDto;
import com.example.amongserver.authorization.exception.AuthorityNotFoundException;
import com.example.amongserver.authorization.exception.UserAlreadyExistsException;
import com.example.amongserver.authorization.exception.UserByEmailNotFoundException;
import com.example.amongserver.authorization.service.UserRegisterService;
import com.example.amongserver.jwt.manager.JwtTokenManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
@SpringBootTest
@AutoConfigureMockMvc
public class UserRegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRegisterService userRegisterService;

    @MockBean
    private JwtTokenManager jwtTokenManager;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        // Отключение CSRF в тестах
        mockMvc = MockMvcBuilders.standaloneSetup(new UserRegisterController(userRegisterService))
                .apply(springSecurity()) // Включение Spring Security
                .build();
    }

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
                .andExpect(status().isCreated())  // Проверка статуса 201 Created
                .andExpect(header().string("Location", nullValue()))  // Проверка, что Location-заголовок отсутствует
                .andExpect(content().string(""));  // Проверка, что тело ответа пустое

        // Проверка, что метод saveUser был вызван один раз с переданным аргументом
        verify(userRegisterService, times(1)).saveUser(argThat(argument ->
                argument.getUsername().equals("testUser") &&
                        argument.getEmail().equals("test@example.com") &&
                        argument.getPassword().equals("password123")
        ));
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
