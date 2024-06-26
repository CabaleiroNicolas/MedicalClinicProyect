package com.medicalClinicProyect.MedicalClinic.dto.requestDto;

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
    @Nullable
    private String profilePhoto;
    @NotBlank(message = "contact number must not be empty")
    private String contactNumber;
    @NotBlank(message = "dni number must not be empty")
    private String dniNumber;
    private String birthday;
    @NotBlank(message = "address must not be empty")
    private String address;

}

