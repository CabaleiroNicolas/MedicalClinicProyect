package com.medicalClinicProyect.MedicalClinic.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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
    private LocalDateTime timestamp = LocalDateTime.now();

}
