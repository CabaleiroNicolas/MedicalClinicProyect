package com.medicalClinicProyect.MedicalClinic.controller;

import com.medicalClinicProyect.MedicalClinic.dto.AcceptOrRejectAccountResponse;
import com.medicalClinicProyect.MedicalClinic.dto.ShowAdministrator;
import com.medicalClinicProyect.MedicalClinic.dto.ShowPatient;
import com.medicalClinicProyect.MedicalClinic.service.AdministratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdministratorController {


    private final AdministratorService administratorService;

    @PutMapping("/accept")
    public ResponseEntity<AcceptOrRejectAccountResponse> acceptAccount(@RequestParam Long id){

        AcceptOrRejectAccountResponse response = administratorService.acceptAccount(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/reject")
    public ResponseEntity<AcceptOrRejectAccountResponse> rejectAccount(@RequestParam Long id){

        AcceptOrRejectAccountResponse response = administratorService.rejectAccount(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ShowAdministrator>> findAllAdministrators(@PageableDefault(size = 5) Pageable pageable){

        List<ShowAdministrator> response = administratorService.findAll(pageable);
        return ResponseEntity.ok(response);
    }
}
