package com.medicalClinicProyect.MedicalClinic.dto.requestDto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileRequest implements Serializable {

    private String name;
    private String lastname;
    private String address;
    private String dniNumber;
    private String contactNumber;
    private String profilePhoto;
}
