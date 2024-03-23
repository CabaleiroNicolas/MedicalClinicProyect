package com.medicalClinicProyect.MedicalClinic.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CancelAppointmentException extends RuntimeException{

    private String message;
}
