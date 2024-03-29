package com.medicalClinicProyect.MedicalClinic.dto.showDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowPatient implements Serializable {

    private Long patientId;
    private String username;
    private String name;
    private String lastname;
    private String age;
    private String contactNumber;

}
