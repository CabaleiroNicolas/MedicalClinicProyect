package com.medicalClinicProyect.MedicalClinic.exception;

import com.medicalClinicProyect.MedicalClinic.dto.ExceptionResponse;
import com.medicalClinicProyect.MedicalClinic.dto.ValidationExceptionResponse;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
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


//this class is in charge to intercept the indicated exceptions in the methods
@RestControllerAdvice
public class GlobalExceptionHandler {


    //Intercept the exceptions of type 'Validation', generated by input parameters
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

        return ResponseEntity.status(response.getStatus()).body(response);
    }



    //Intercept the SQL exceptions, such as, a repeated username error when add a new user to DB
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> MethodArgumentNotValidException(SQLIntegrityConstraintViolationException ex, HttpServletRequest request){

        ExceptionResponse response = new ExceptionResponse();
        response.setBackendMessage("Username is not available");
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setMethod(request.getMethod());
        response.setEndpoint(request.getRequestURI().toString());

        return ResponseEntity.status(response.getStatus()).body(response);
    }


    //is throw when the password and repeat password not matches in a register process
    @ExceptionHandler(PasswordNotMatchesException.class)
    public ResponseEntity<ExceptionResponse> PasswordNotMatchesException(PasswordNotMatchesException ex, HttpServletRequest request){

        ExceptionResponse response = new ExceptionResponse();
        response.setBackendMessage(ex.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setMethod(request.getMethod());
        response.setEndpoint(request.getRequestURI().toString());

        return ResponseEntity.status(response.getStatus()).body(response);
    }


    //is throw when a resource not exist was solicited (in the instance you must specify which resource type was not found)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> ResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request){

        ExceptionResponse response = new ExceptionResponse();
        response.setBackendMessage(ex.getResource()+" not found");
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setMethod(request.getMethod());
        response.setEndpoint(request.getRequestURI().toString());

        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
