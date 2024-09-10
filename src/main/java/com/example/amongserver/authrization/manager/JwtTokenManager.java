package com.example.amongserver.authrization.manager;


import com.example.amongserver.registration.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Component
public class JwtTokenManager {

    private final Key key;

    @Value("${spring.security.jwt.access-token-validity}")
    private long validityInSeconds;


    public JwtTokenManager(@Value("${spring.security.jwt.secret}") String secret) {
        this.key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), HS256.getJcaName());
    }


    // Метод генерации JWT токена
    public String generateJwtToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> authorities = userDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).toList();
        claims.put("authorities", authorities);
        return createToken(claims, ((User) userDetails).getEmail());
    }


    // Метод создания JWT токена
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validityInSeconds * 1000))
                .signWith(key, HS256)
                .compact();
    }


    // Получение email из токена
    public String getEmailFromToken (String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    // Получение ролей пользователя из токена

    public List<String> getUserRoles(String token) {
        return getAllClaimsFromToken(token).get("authorities", List.class);
    }
//    public List<String> getUserRoles(String token) {
//        return (List<String>) getAllClaimsFromToken(token).get("authorities", List.class); // Приведение к List<String>
//    }

    // Получение всех claims из токена
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Проверка токена на валидность

    public boolean validateToken(String token, UserDetails userDetails) {
        String email = getEmailFromToken(token);
        return email.equals(((User) userDetails).getEmail()) && !isTokenExpired(token);
    }

    // Проверка, истёк ли токен
    private boolean isTokenExpired(String token) {
        Date expiration = getAllClaimsFromToken(token).getExpiration();
        return expiration.before(new Date());
    }
}

