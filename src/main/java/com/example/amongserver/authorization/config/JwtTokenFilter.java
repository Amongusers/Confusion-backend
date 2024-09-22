package com.example.amongserver.authorization.config;

import com.example.amongserver.authorization.exception.UserByIdNotFoundException;
import com.example.amongserver.authorization.manager.JwtTokenManager;
import com.example.amongserver.registration.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenManager jwtTokenManager;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;

    @Override
    // TODO : нужно реализовать исключения
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Получаем токен из запроса
            String token = getTokenFromRequest(request);

            // Проверяем, что токен не null
            if (token != null && SecurityContextHolder.getContext().getAuthentication() == null &&
                    jwtTokenManager.validateToken(token)) {
                // Получаем ID пользователя из токена
                Long id = jwtTokenManager.getIdFromToken(token);

                if (id != null) {
                    // Загружаем пользователя по email
                    // TODO : стоит подумать чтобы вынести слой сервисов, если вынести метод в UserAuthService, то будет циклическая зависимость
                    UserDetails userDetails = userRepository.findById(id).orElseThrow(
                            () -> new UserByIdNotFoundException("User with ID "
                                    + id
                                    + " not found"));

                    // Создаём объект аутентификации
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    // Устанавливаем дополнительные параметры аутентификации
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Устанавливаем аутентификацию в SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (MalformedJwtException exception) {
            return;
        } catch (ExpiredJwtException exception) {
            return;
        } catch (SignatureException exception) {
            return;
        }


        // Передаём управление дальше по цепочке фильтров
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Убираем префикс "Bearer "
        }
        return null;
    }
}
