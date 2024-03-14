package com.medicalClinicProyect.MedicalClinic.controller;

import com.medicalClinicProyect.MedicalClinic.dto.RegisterPatientRequest;
import com.medicalClinicProyect.MedicalClinic.dto.RegisterResponse;
import com.medicalClinicProyect.MedicalClinic.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/auth/register")
    public ResponseEntity<RegisterResponse> registerPatient(@Valid @RequestBody RegisterPatientRequest request){

        RegisterResponse response = patientService.register(request);
        return ResponseEntity.ok(response);
    }

}
