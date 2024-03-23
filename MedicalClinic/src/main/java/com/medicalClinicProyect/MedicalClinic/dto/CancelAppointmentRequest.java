package com.medicalClinicProyect.MedicalClinic.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CancelAppointmentRequest implements Serializable {

    @Size(max = 300)
    private String cause;
}
