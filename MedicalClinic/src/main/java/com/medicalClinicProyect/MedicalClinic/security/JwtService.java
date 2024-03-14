package com.medicalClinicProyect.MedicalClinic.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@RequiredArgsConstructor
public class JwtService {

    @Value("${spring.security.secret_key}")
    private final String SECRET_KEY;
    @Value("${spring.security.jwt.expiration_in_minutes}")
    private final int EXPIRATION_IN_MINUTES;

    public String generateToken(Map<String, String> extraClaims, UserDetails user){

        Date now = new Date(System.currentTimeMillis();

        return Jwts.builder()
                .header().type("JWT").and()
                .subject(user.getUsername())
                .signWith(generateKey(),Jwts.SIG.HS256)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + (1000L * 60 * EXPIRATION_IN_MINUTES)))
                .claims(extraClaims)
                .compact();
    }

    public String extractUsername(String jwt) throws JwtException{
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload()
                .getSubject();
    }

    private SecretKey generateKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

}
