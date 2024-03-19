package com.medicalClinicProyect.MedicalClinic.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ResourceNotFoundException extends RuntimeException{

    private String resource;
}
