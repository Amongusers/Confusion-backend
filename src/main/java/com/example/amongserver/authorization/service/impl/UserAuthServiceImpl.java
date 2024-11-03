package com.example.amongserver.authorization.service.impl;

import com.example.amongserver.domain.User;
import com.example.amongserver.authorization.dto.UserAuthRequestDto;
import com.example.amongserver.authorization.dto.UserAuthResponseDto;
import com.example.amongserver.authorization.manager.JwtTokenManager;
import com.example.amongserver.authorization.service.UserAuthService;
import com.example.amongserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtTokenManager jwtTokenManager;
    private final UserRepository userRepository;


    @Override
    public UserAuthResponseDto authUser(UserAuthRequestDto userAuthRequestDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAuthRequestDto.getEmail(), userAuthRequestDto.getPassword()));
        User user = (User) userDetailsService.loadUserByUsername(userAuthRequestDto.getEmail());
        String token = jwtTokenManager.generateJwtToken(user);
        return UserAuthResponseDto.builder().email(user.getEmail())
                .username(user.getUsername())
                .token(token).build();
    }
}


