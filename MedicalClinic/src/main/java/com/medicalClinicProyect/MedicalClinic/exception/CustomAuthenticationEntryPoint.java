package com.medicalClinicProyect.MedicalClinic.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.medicalClinicProyect.MedicalClinic.dto.responseDto.ExceptionResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;


//This class is in charge to handle the cases when don't exist authentication token in the request header or the same isn't valid
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        ExceptionResponse apiError = new ExceptionResponse();
        apiError.setBackendMessage("Jwt isn't present or invalid");
        apiError.setStatus(HttpStatus.UNAUTHORIZED);
        apiError.setMethod(request.getMethod());
        apiError.setEndpoint(request.getRequestURI());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String dtoAsJson = mapper.writeValueAsString(apiError);
        response.getWriter().write(dtoAsJson);

    }
}
