package com.medicalClinicProyect.MedicalClinic.security;

import com.medicalClinicProyect.MedicalClinic.exception.ResourceNotFoundException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.lang.Strings;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;


//This is the authentication filter that will be placed in the security filter chain
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        //extract header to http request and look for a 'Bearer token' into him
        //if don´t exist a 'Bearer token' or already there is an Authentication instance (already authenticated user) in the security context
        //continue the filter chain without valid
        String header = request.getHeader("Authorization");
        if(!Strings.hasText(header) || !header.startsWith("Bearer ") || SecurityContextHolder.getContext().getAuthentication() != null){
            filterChain.doFilter(request,response);
            return;
        }

        //take out jwt from header
        String jwt = header.replace("Bearer ","");


        //extract 'principal' from payload
        String username = jwtService.extractUsername(jwt);

        //get User from DB
        Optional<UserDetails> user = Optional.ofNullable(userDetailsService.loadUserByUsername(username));

        if(user.isEmpty()){
            throw new ResourceNotFoundException("Principal in JWT");
        }

        //create an Authentication object, set additional details from request and update security context with the new 'principal'
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.get(),null,user.get().getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //continue filter chain
        filterChain.doFilter(request,response);
    }
}
