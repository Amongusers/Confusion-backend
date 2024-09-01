package com.example.amongserver.authorization.contoller;

import com.example.amongserver.authorization.dto.UserLoginRequestDto;
import com.example.amongserver.authorization.dto.UserLoginResponseDto;
import com.example.amongserver.authorization.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserAuthController {

    private final UserAuthService userAuthService;

    @PostMapping
    public ResponseEntity<UserLoginResponseDto> authenticateUser(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        String token = userAuthService.getToken(userLoginRequestDto);
        return ResponseEntity.ok(new UserLoginResponseDto(token));
    }


    // TODO : метод ниже не проверен, скорее всего и не нужен
//    @PostMapping
//    public ResponseEntity<UserLoginResponseDto> authenticateUsers(@RequestBody UserLoginRequestDto loginRequest) {
//        // Аутентификация пользователя
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginRequest.getEmail(),
//                        loginRequest.getPassword()
//                )
//        );
//
//        // Получение данных пользователя
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
//
//        // Генерация JWT токена
//        String jwtToken = jwtService.generateToken(userDetails);
//
//        // Формирование ответа
//        return ResponseEntity.ok(new UserLoginResponseDto(jwtToken));
//    }
}
