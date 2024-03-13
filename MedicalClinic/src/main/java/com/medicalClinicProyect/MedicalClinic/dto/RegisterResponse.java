package com.medicalClinicProyect.MedicalClinic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {

    private String message;
    private String jwt;
    private String username;
    private String name;
    private String lastName;
    private Date issueAt = new Date(System.currentTimeMillis());

}
