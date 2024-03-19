package com.medicalClinicProyect.MedicalClinic.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class ChangePasswordRequest implements Serializable {

    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;
}
