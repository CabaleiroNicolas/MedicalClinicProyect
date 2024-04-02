package com.medicalClinicProyect.MedicalClinic.security;


import com.medicalClinicProyect.MedicalClinic.exception.ResourceNotFoundException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class JwtService {

    @Value("${spring.security.secret_key}")
    private String SECRET_KEY;
    @Value("${spring.security.jwt.expiration_in_minutes}")
    private Long EXPIRATION_IN_MINUTES;


    //generate a new JWT
    public String generateToken(Map<String, Object> extraClaims, UserDetails user){

        Date now = new Date(System.currentTimeMillis());
        return Jwts.builder()
                .header().type("JWT").and()
                .subject(user.getUsername())
                .signWith(generateKey(),Jwts.SIG.HS256)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + (1000L * 60 * EXPIRATION_IN_MINUTES)))
                .claims(extraClaims)
                .compact();
    }


    //extract principal from jwt
    public String extractUsername(String jwt){

        try {
            return Jwts.parser()
                    .verifyWith(generateKey())
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload()
                    .getSubject();
        }catch (Exception e) {
            return null;
        }
    }

    //generate an encoded secret key with 'HMACSHA' algorithm
    private SecretKey generateKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

}
