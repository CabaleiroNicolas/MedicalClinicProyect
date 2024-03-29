package com.medicalClinicProyect.MedicalClinic.controller;

import com.medicalClinicProyect.MedicalClinic.dto.requestDto.RegisterProfessionalRequest;
import com.medicalClinicProyect.MedicalClinic.dto.responseDto.RegisterResponse;
import com.medicalClinicProyect.MedicalClinic.dto.showDto.ShowProfessional;
import com.medicalClinicProyect.MedicalClinic.service.ProfessionalService;
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
@RequestMapping("/professional")
public class ProfessionalController {

    private final ProfessionalService professionalService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerPatient(@Valid @RequestBody RegisterProfessionalRequest request) throws SQLIntegrityConstraintViolationException {

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


}
