package com.medicalClinicProyect.MedicalClinic.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter @Setter
@AllArgsConstructor
public class RegisterPatientRequest implements Serializable {

    @Size(min = 4, max = 10, message = "Username must be between 4 and 15 characters")
    @NotBlank
    private String username;
    @NotBlank
    @Size(min = 4, max = 15, message = "Password must be between 4 and 15 characters")
    private String password;
    @NotBlank(message = "confirm password must not be empty")
    private String confirmPassword;
    @NotBlank(message = "name must not be empty")
    private String name;
    @NotBlank(message = "lastname must not be empty")
    private String lastname;
    @Nullable
    private String profilePhoto;
    @NotBlank(message = "contact number must not be empty")
    private String contactNumber;
    @NotBlank(message = "dni number must not be empty")
    private String dniNumber;
    @Positive(message = "age must be a positive number")
    private Long age;
    @NotBlank(message = "address must not be empty")
    private String address;

}

