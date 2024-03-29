package com.medicalClinicProyect.MedicalClinic.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse implements Serializable {

    private String message;
    private String jwt;

}
