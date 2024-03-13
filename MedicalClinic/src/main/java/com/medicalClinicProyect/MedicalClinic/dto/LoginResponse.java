package com.medicalClinicProyect.MedicalClinic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@AllArgsConstructor
public class LoginResponse implements Serializable {

    private String message;
    private String jwt;

}
