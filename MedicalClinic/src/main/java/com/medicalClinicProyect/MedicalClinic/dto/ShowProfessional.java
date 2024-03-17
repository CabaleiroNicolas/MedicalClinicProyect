package com.medicalClinicProyect.MedicalClinic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@AllArgsConstructor@NoArgsConstructor
public class ShowProfessional implements Serializable {

    private Long professionalId;
    private String name;
    private String lastname;
    private String profilePhoto;
    private String speciality;

}
