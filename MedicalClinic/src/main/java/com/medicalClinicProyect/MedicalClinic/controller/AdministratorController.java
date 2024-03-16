package com.medicalClinicProyect.MedicalClinic.controller;

import com.medicalClinicProyect.MedicalClinic.dto.AcceptOrRejectAccountResponse;
import com.medicalClinicProyect.MedicalClinic.service.AdministratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdministratorController {


    private final AdministratorService administratorService;

    @PatchMapping("/accept")
    public ResponseEntity<AcceptOrRejectAccountResponse> acceptAccount(@RequestParam Long id){

        AcceptOrRejectAccountResponse response = administratorService.acceptAccount(id);
        return ResponseEntity.ok(response);
    }
}
