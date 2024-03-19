package com.medicalClinicProyect.MedicalClinic.exception;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class WrongAccountRequestException extends RuntimeException{

    private final String message = "Request to wrong account";
}
