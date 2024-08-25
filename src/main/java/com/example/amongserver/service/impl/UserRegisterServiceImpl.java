package com.example.amongserver.service.impl;

import com.example.amongserver.domain.entity.Authority;
import com.example.amongserver.domain.entity.User;
import com.example.amongserver.dto.UserProfileDto;
import com.example.amongserver.dto.UserRegisterRequestDto;
import com.example.amongserver.exception.AuthorityNotFoundException;
import com.example.amongserver.exception.UserAlreadyExistsException;
import com.example.amongserver.mapper.UserProfileMapper;
import com.example.amongserver.mapper.UserRegisterMapper;
import com.example.amongserver.reposirory.AuthorityRepository;
import com.example.amongserver.reposirory.UserRepository;
import com.example.amongserver.service.UserRegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserRegisterServiceImpl implements UserRegisterService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserProfileDto add(UserRegisterRequestDto userRegisterDto) {
        if (userRepository.findByEmail(userRegisterDto.getEmail()).isPresent())
            throw new UserAlreadyExistsException("User with email "
                    + userRegisterDto.getEmail() +  " already exists");


        Authority authority = authorityRepository.findByAuthority("ROLE_USER")
                .orElseThrow(() -> new AuthorityNotFoundException("Authority not found!"));

        User user = UserRegisterMapper.toUserEntity(userRegisterDto);
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority);
        user.setAuthorities(authorities);
        userRepository.save(user);
        return UserProfileMapper.toUserProfileDto(user);

    }
}
