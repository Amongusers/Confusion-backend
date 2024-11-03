package com.example.amongserver.registration.service;

import com.example.amongserver.domain.Authority;
import com.example.amongserver.domain.User;
import com.example.amongserver.registration.controller.dto.UserRegisterRequestDto;
import com.example.amongserver.registration.exception.AuthorityNotFoundException;
import com.example.amongserver.registration.exception.UserAlreadyExistsException;
import com.example.amongserver.registration.mapper.UserRegisterMapper;
import com.example.amongserver.repository.AuthorityRepository;
import com.example.amongserver.repository.UserRepository;
import com.example.amongserver.registration.service.impl.UserRegisterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRegisterServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthorityRepository authorityRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserRegisterServiceImpl userRegisterService;

    private UserRegisterRequestDto userRegisterRequestDto;

    @BeforeEach
    void setUp() {
        userRegisterRequestDto = UserRegisterRequestDto.builder()
                .email("test@example.com")
                .password("password")
                .username("testUser")
                .build();
    }

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Test
    void saveUser_withValidData_saveUser() {
        // Arrange
        when(userRepository.findByEmail(userRegisterRequestDto.getEmail()))
                .thenReturn(Optional.empty()); // Пользователь не найден
        when(authorityRepository.findByAuthority("ROLE_USER"))
                .thenReturn(Optional.of(new Authority(1L, "ROLE_USER"))); // Роль найдена
        when(passwordEncoder.encode(userRegisterRequestDto.getPassword()))
                .thenReturn("encodedPassword"); // Шифруем пароль

        User user = UserRegisterMapper.toUserEntity(userRegisterRequestDto);
        user.setPassword("encodedPassword");
        Set<Authority> authorities = new HashSet<>();
        authorities.add(new Authority(1L, "ROLE_USER"));
        user.setAuthorities(authorities);
        when(userRepository.save(any(User.class)))
                .thenReturn(user); // Сохраняем пользователя

        // Act
        userRegisterService.saveUser(userRegisterRequestDto);



        // Assert
        verify(userRepository, times(1)).findByEmail(userRegisterRequestDto.getEmail());
        verify(authorityRepository, times(1)).findByAuthority("ROLE_USER");
        verify(passwordEncoder, times(1)).encode(userRegisterRequestDto.getPassword());
        verify(userRepository, times(1)).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();
        assertThat(capturedUser.getPassword()).isEqualTo("encodedPassword");
        assertThat(capturedUser.getEmail()).isEqualTo("test@example.com");
        assertThat(capturedUser.getUsername()).isEqualTo("testUser");
        assertThat(capturedUser.getAuthorities())
                .extracting(Authority::getAuthority)
                .containsExactly("ROLE_USER");

    }

    @Test
    void saveUser_withExistingUser_throwsUserAlreadyExistsException() {
        // Arrange
        User existingUser = new User();
        existingUser.setEmail(userRegisterRequestDto.getEmail());

        // Настраиваем mock для userRepository, чтобы возвращать существующего пользователя
        when(userRepository.findByEmail(userRegisterRequestDto.getEmail()))
                .thenReturn(Optional.of(existingUser));

        // Act & Assert
        assertThatExceptionOfType(UserAlreadyExistsException.class)
                .isThrownBy(() -> userRegisterService.saveUser(userRegisterRequestDto))
                .withMessage("User with email test@example.com already exists");

        // Проверяем, что остальные методы не были вызваны
        verify(userRepository, times(1)).findByEmail(userRegisterRequestDto.getEmail());
        verify(authorityRepository, never()).findByAuthority(anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void saveUser_withNonExistingAuthority_throwsAuthorityNotFoundException() {
        // Arrange
        when(userRepository.findByEmail(userRegisterRequestDto.getEmail()))
                .thenReturn(Optional.empty()); // Пользователь не найден
        when(authorityRepository.findByAuthority("ROLE_USER"))
                .thenReturn(Optional.empty()); // Роль не найдена

        // Act & Assert
        assertThatExceptionOfType(AuthorityNotFoundException.class)
                .isThrownBy(() -> userRegisterService.saveUser(userRegisterRequestDto))
                .withMessage("Authority not found!");

        // Проверяем, что остальные методы не вызываются
        verify(userRepository, times(1)).findByEmail(userRegisterRequestDto.getEmail());
        verify(authorityRepository, times(1)).findByAuthority("ROLE_USER");
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }
}

