package com.medicalClinicProyect.MedicalClinic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfessionalRequest implements Serializable {

    private String name;
    private String lastname;
    private String contactNumber;
    private String profilePhoto;
}
