package com.medicalClinicProyect.MedicalClinic.controller;

import com.medicalClinicProyect.MedicalClinic.dto.requestDto.RegisterPatientRequest;
import com.medicalClinicProyect.MedicalClinic.dto.responseDto.RegisterResponse;
import com.medicalClinicProyect.MedicalClinic.dto.showDto.ShowPatient;
import com.medicalClinicProyect.MedicalClinic.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerPatient(@Valid @RequestBody RegisterPatientRequest request) throws SQLIntegrityConstraintViolationException {

        RegisterResponse response = patientService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ShowPatient>> findAllPatients(@PageableDefault(size = 5) Pageable pageable){

        List<ShowPatient> response = patientService.findAll(pageable);
        return ResponseEntity.ok(response);
    }


}
