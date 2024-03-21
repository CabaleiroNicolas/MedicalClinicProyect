package com.medicalClinicProyect.MedicalClinic.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class AppointmentNotAvailableException extends RuntimeException{

    private final String message = "Appointment not available";
}
