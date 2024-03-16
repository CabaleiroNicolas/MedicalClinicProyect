package com.medicalClinicProyect.MedicalClinic.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationException extends RuntimeException{
    private String message;
}
