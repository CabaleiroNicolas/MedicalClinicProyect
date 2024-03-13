package com.medicalClinicProyect.MedicalClinic.dto;

import com.medicalClinicProyect.MedicalClinic.entity.Speciality;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@AllArgsConstructor
public class RegisterProfessionalRequest implements Serializable {

    private String username;
    private String password;
    private String confirmPassword;
    private String name;
    private String lastname;
    private String profilePhoto;
    private String contactNumber;
    private Speciality speciality;


}
