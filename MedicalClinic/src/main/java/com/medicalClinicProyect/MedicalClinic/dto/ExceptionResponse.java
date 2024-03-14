package com.medicalClinicProyect.MedicalClinic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Date;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse implements Serializable {

    private String backendMessage;
    private HttpStatus status;
    private String method;
    private String endpoint;
    private Date issueAt = new Date();

}
