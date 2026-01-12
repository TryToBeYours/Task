package org.example.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Base64;

@Component
public class JwtUtils {

    @Value("${jwt.secret:}")
    private String base64Key;

    private SecretKey key;

    private final long jwtExpirationMs = 86400000;

    @PostConstruct
    public void init() {
        try {
            if (base64Key == null || base64Key.isBlank()) {
                key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
                base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
                System.out.println("Generated new JWT key: " + base64Key);
            } else {
                key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64Key));
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid JWT secret, generating a new secure key...");
            key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
            System.out.println("Generated new JWT key: " + base64Key);
        }
    }

    public String generateJwt(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateJwt(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public String getBase64Key() {
        return base64Key;
    }
}
