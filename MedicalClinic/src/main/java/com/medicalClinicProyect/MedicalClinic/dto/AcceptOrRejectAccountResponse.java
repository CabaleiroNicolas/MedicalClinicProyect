package com.medicalClinicProyect.MedicalClinic.dto;

import com.medicalClinicProyect.MedicalClinic.entity.Administrator;
import com.medicalClinicProyect.MedicalClinic.entity.Professional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AcceptOrRejectAccountResponse {

    private String message;
    private Long requestId;
    private String professionalName;
    private String professionalLastname;
    private String status;
    private String by;
    private Date issueAtRequest;
    private Date issueAtResponse = new Date();
}
