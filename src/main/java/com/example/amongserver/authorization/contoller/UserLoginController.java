package com.example.amongserver.authorization.contoller;

import com.example.amongserver.authorization.UserLoginRequestDto;
import com.example.amongserver.authorization.UserLoginResponseDto;
import com.example.amongserver.registration.service.JwtService;
import com.example.amongserver.registration.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class UserLoginController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @PostMapping
    public ResponseEntity<UserLoginResponseDto> authenticateUser(@RequestBody UserLoginRequestDto loginRequest) {
        // Аутентификация пользователя
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        // Получение данных пользователя
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

        // Генерация JWT токена
        String jwtToken = jwtService.generateToken(userDetails);

        // Формирование ответа
        return ResponseEntity.ok(new UserLoginResponseDto(jwtToken));
    }
}
