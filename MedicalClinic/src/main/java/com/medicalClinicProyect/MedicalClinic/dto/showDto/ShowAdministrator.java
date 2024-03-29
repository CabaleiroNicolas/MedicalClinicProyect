package com.medicalClinicProyect.MedicalClinic.dto.showDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowAdministrator implements Serializable {

    private Long administratorId;
    private String username;
}
