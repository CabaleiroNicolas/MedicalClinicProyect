package com.medicalClinicProyect.MedicalClinic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
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
    private String profilePhoto;
    @NotBlank(message = "contact number must not be empty")
    private String contactNumber;
    @NotBlank(message = "dni number must not be empty")
    private String dniNumber;
    @NotBlank(message = "age must not be empty")
    @Positive(message = "age must be a positive number")
    private Long age;
    @NotBlank(message = "address must not be empty")
    private String address;

}
