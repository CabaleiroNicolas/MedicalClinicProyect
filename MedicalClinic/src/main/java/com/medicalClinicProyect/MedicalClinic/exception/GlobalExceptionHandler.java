package com.medicalClinicProyect.MedicalClinic.exception;

import com.medicalClinicProyect.MedicalClinic.dto.ValidationExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionResponse> MethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request){

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(each -> {
            String key = ((FieldError)each).getField();
            String value = each.getDefaultMessage();
            errors.put(key,value);
        });

        ValidationExceptionResponse response = new ValidationExceptionResponse();
        response.setBackendMessage("Fields Validation Error");
        response.setErrors(errors);
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setMethod(request.getMethod());
        response.setEndpoint(request.getRequestURI().toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ValidationExceptionResponse> MethodArgumentNotValidException(SQLIntegrityConstraintViolationException ex, HttpServletRequest request){

        ValidationExceptionResponse response = new ValidationExceptionResponse();
        response.setBackendMessage("Username is not available");
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setMethod(request.getMethod());
        response.setEndpoint(request.getRequestURI().toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
