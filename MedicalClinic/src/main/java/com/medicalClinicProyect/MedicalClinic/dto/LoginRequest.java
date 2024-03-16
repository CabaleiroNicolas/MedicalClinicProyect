package com.medicalClinicProyect.MedicalClinic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest implements Serializable {

    private String username;
    private String password;
}
