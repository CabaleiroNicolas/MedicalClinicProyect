package com.medicalClinicProyect.MedicalClinic.dto;

import com.medicalClinicProyect.MedicalClinic.entity.Speciality;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter @Setter
@AllArgsConstructor
public class RegisterProfessionalRequest implements Serializable {

    @Size(min = 4, max = 10, message = "Username must be between 4 and 10 characters")
    @NotBlank(message = "Username not must be empty")
    private String username;
    @NotBlank(message = "Password not must be empty")
    @Size(min = 4, max = 15, message = "Password must be between 4 and 15 characters")
    private String password;
    private String confirmPassword;
    @NotBlank(message = "name must not be empty")
    private String name;
    @NotBlank(message = "lastname must not be empty")
    private String lastname;
    private String profilePhoto;
    @NotBlank(message = "contact number must not be empty")
    private String contactNumber;
    @NotBlank(message = "speciality must not be empty")
    private String speciality;


}
