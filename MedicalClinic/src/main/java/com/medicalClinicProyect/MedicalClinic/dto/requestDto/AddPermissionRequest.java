package com.medicalClinicProyect.MedicalClinic.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddPermissionRequest implements Serializable {

    private String roleName;
    private Long authorityId;
}
