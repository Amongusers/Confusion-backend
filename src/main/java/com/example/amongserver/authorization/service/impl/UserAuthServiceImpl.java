package com.example.amongserver.authorization.service.impl;

import com.example.amongserver.authorization.dto.UserLoginRequestDto;
import com.example.amongserver.authorization.service.UserAuthService;
import com.example.amongserver.registration.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    @Override
    public String getToken(UserLoginRequestDto userLoginRequestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginRequestDto.getEmail(),
                        userLoginRequestDto.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(userLoginRequestDto.getEmail());
        return jwtService.generateToken(userDetails);
    }
}
