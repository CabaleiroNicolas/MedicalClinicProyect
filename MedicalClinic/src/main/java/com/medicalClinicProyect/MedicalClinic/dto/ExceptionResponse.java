package com.medicalClinicProyect.MedicalClinic.dto;

import com.medicalClinicProyect.MedicalClinic.util.UtilityMethods;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse implements Serializable {

    private String backendMessage;
    private HttpStatus status;
    private String method;
    private String endpoint;
    private final String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("d/M/yyyy h:m"));

}
