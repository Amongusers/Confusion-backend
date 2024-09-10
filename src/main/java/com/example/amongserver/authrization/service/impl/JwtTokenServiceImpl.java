package com.example.amongserver.authrization.service.impl;

import com.example.amongserver.registration.domain.User;
import com.example.amongserver.authrization.dto.UserAuthRequestDto;
import com.example.amongserver.authrization.dto.UserAuthResponseDto;
import com.example.amongserver.authrization.manager.JwtTokenManager;
import com.example.amongserver.authrization.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtTokenManager jwtTokenManager;


    public UserAuthResponseDto authUser(UserAuthRequestDto userAuthRequestDto) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAuthRequestDto.getEmail(), userAuthRequestDto.getPassword()));
    UserDetails userDetails = userDetailsService.loadUserByUsername(userAuthRequestDto.getEmail());
    String token = jwtTokenManager.generateJwtToken(userDetails);

        User user = (User) userDetails;
    return UserAuthResponseDto.builder().email(user.getEmail())
            .username(user.getUsername())
            .token(token).build();
}
}


