package com.medicalClinicProyect.MedicalClinic.dto.showDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowReport implements Serializable {

    private Long appointmentId;
    private String professional;
    private String patient;
    private String observations;
    private LocalDateTime appointmentDate;
    private LocalDateTime reportDate;
}
