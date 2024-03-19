package com.medicalClinicProyect.MedicalClinic.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class PasswordNotMatchesException extends RuntimeException{

    private final String message = "Passwords not matches";

}
