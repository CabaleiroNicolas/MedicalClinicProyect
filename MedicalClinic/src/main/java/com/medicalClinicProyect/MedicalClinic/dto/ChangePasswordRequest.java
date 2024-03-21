package com.medicalClinicProyect.MedicalClinic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest implements Serializable {

    private String oldPassword;
    @NotBlank(message = "Password not must be empty")
    @Size(min = 4, max = 15, message = "Password must be between 4 and 15 characters")
    private String newPassword;
    private String confirmNewPassword;
}
