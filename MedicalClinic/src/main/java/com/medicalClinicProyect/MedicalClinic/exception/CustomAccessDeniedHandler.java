package com.medicalClinicProyect.MedicalClinic.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.medicalClinicProyect.MedicalClinic.dto.responseDto.ExceptionResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


//This class is in charge to handle the cases when a user authenticated try access to resource for which don't have permissions
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        ExceptionResponse apiError = new ExceptionResponse();
        apiError.setStatus(HttpStatus.FORBIDDEN);
        apiError.setEndpoint(String.valueOf(request.getRequestURI()));
        apiError.setMethod(request.getMethod());
        apiError.setBackendMessage("Access Denied, you not have the need permissions for access to this function");

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String dtoAsJson = mapper.writeValueAsString(apiError);
        response.getWriter().write(dtoAsJson);

    }
}
