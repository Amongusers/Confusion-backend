package com.example.amongserver.authorization.service;

import com.example.amongserver.authorization.domain.Authority;
import com.example.amongserver.authorization.domain.User;
import com.example.amongserver.authorization.dto.UserProfileDto;
import com.example.amongserver.authorization.dto.UserRegisterRequestDto;
import com.example.amongserver.authorization.mapper.UserRegisterMapper;
import com.example.amongserver.authorization.repository.AuthorityRepository;
import com.example.amongserver.authorization.repository.UserRepository;
import com.example.amongserver.authorization.service.impl.UserRegisterServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRegisterServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthorityRepository authorityRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserRegisterServiceImpl userRegisterService;

    @Captor
    ArgumentCaptor<User> userCaptor;

    @Test
    void whenAddUser_thenUserShouldBeSaved() {
        // Arrange
        UserRegisterRequestDto requestDto = new UserRegisterRequestDto();
        requestDto.setEmail("test@example.com");
        requestDto.setPassword("plainPassword");

        Authority authority = Authority.builder()
                .id(1L)
                .authority("ROLE_USER")
                .build();

        User user = UserRegisterMapper.toUserEntity(requestDto);
        user.setPassword("encodedPassword");
        user.setAuthorities(Set.of(authority));

        when(userRepository.findByEmail(requestDto.getEmail())).thenReturn(Optional.empty());
        when(authorityRepository.findByAuthority("ROLE_USER")).thenReturn(Optional.of(authority));
        when(passwordEncoder.encode(requestDto.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        UserProfileDto result = userRegisterService.add(requestDto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("test@example.com");
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertThat(savedUser.getEmail()).isEqualTo("test@example.com");
        assertThat(savedUser.getPassword()).isEqualTo("encodedPassword");
//        assertThat(savedUser.getAuthorities()).containsExactly(authority);
    }
}
