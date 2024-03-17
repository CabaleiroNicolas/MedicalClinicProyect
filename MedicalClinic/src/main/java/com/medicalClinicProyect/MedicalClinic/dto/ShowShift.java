package com.medicalClinicProyect.MedicalClinic.dto;

import com.medicalClinicProyect.MedicalClinic.entity.Professional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowShift {

    private Long shiftId;
    private String professionalName;
    private String professionalSpeciality;
    private String patientName;
    private String status;
    private Date date;
}
