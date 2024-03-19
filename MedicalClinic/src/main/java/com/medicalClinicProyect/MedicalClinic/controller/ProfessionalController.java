package com.medicalClinicProyect.MedicalClinic.controller;

import com.medicalClinicProyect.MedicalClinic.dto.*;
import com.medicalClinicProyect.MedicalClinic.entity.Professional;
import com.medicalClinicProyect.MedicalClinic.service.PatientService;
import com.medicalClinicProyect.MedicalClinic.service.ProfessionalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/professional")
public class ProfessionalController {

    private final ProfessionalService professionalService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerPatient(@Valid @RequestBody RegisterProfessionalRequest request){

        RegisterResponse response = professionalService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ShowProfessional>> findAcceptedProfessionals(@PageableDefault(size = 5) Pageable pageable){

        List<ShowProfessional> response = professionalService.findAcceptedProfessionals(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ShowProfessional>> findAllProfessionals(@PageableDefault(size = 5) Pageable pageable){

        List<ShowProfessional> response = professionalService.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pendient")
    public ResponseEntity<List<ShowProfessional>> findPendientProfessionals(@PageableDefault(size = 5) Pageable pageable){

        List<ShowProfessional> response = professionalService.findPendientProfessionals(pageable);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProfileProfessional(@PathVariable Long id, @RequestBody UpdateProfessionalRequest update){

        professionalService.updateProfile(id,update);
        return ResponseEntity.ok("Data Updated Successfully");
    }



}
