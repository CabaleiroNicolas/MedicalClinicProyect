package com.medicalClinicProyect.MedicalClinic.controller;

import com.medicalClinicProyect.MedicalClinic.dto.*;
import com.medicalClinicProyect.MedicalClinic.entity.Professional;
import com.medicalClinicProyect.MedicalClinic.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerPatient(@Valid @RequestBody RegisterPatientRequest request){

        RegisterResponse response = patientService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ShowPatient>> findAllPatients(@PageableDefault(size = 5) Pageable pageable){

        List<ShowPatient> response = patientService.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProfilePatient(@PathVariable Long id, @RequestBody UpdatePatientRequest update){

        patientService.updateProfile(id,update);
        return ResponseEntity.ok("Data Updated Successfully");
    }


}
