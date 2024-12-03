package com.protrader.authentication.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
@Component
public class JwtUtil {

    private final SecretKey key;

    public JwtUtil(@Value("${spring.jwt.secret-key:}") String secretKey) {
        System.out.println("Loaded secret key: " + secretKey); // DEBUG
        if (secretKey == null || secretKey.isEmpty()) {
            this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
            System.out.println("Generated dynamic secret key."); // DEBUG
        } else {
            try {
                byte[] decodedKey = Base64.getDecoder().decode(secretKey);
                this.key = Keys.hmacShaKeyFor(decodedKey);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid Base64 encoded JWT secret key provided.", e);
            }
        }
    }


    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 saat geçerlilik süresi
                .signWith(key, SignatureAlgorithm.HS256) // Güvenli key kullanımı
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    public String extractEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key) // Key ile doğrulama
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(key) // Key ile doğrulama
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}
