package com.bortolanza.fleet.modules.auth.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class TokenUtil {

    public static final long EXPIRATION = 60 * 60 * 1000;
    public static final String SECRET_KEY = "${jwt.secret}";

    private SecretKey getSecretKey() {
        byte[] key = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(key);
    }

    public static String generateToken(String username) {
        try {
            Key key = Keys.hmacShaKeyFor(
                    Base64.getDecoder().decode(SECRET_KEY)
            );

            String jwtToken = Jwts.builder()
                    .subject(username)
                    .issuedAt(new Date())
                    .expiration(
                            new Date(
                                    System.currentTimeMillis()
                                            + EXPIRATION
                            )
                    )
                    .signWith(key)
                    .compact();

            return jwtToken;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractEmailToken(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, String username) {
        return extractEmailToken(token).equals(username) && !isTokenExpired(token);
    }

}
