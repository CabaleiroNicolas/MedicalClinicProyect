package com.medicalClinicProyect.MedicalClinic.dto.showDto;

import com.medicalClinicProyect.MedicalClinic.entity.Professional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowAppointment {

    private Long appointmentId;
    private String professionalName;
    private String professionalSpeciality;
    private String patientName;
    private String status;
    private LocalDateTime date;
}
