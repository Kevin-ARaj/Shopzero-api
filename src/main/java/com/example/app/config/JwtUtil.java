package com.example.app.config;

import java.security.Key;
import jakarta.annotation.PostConstruct;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {	
	
	@Value("${jwt.secret}")
    private String secret;
	
	private Key key;
	
	@PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }
	
	public String generateTokens(String email) {
		return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
	}
	
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    
}
