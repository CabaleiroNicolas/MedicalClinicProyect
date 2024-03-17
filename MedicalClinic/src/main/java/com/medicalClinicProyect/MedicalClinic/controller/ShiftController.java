package com.medicalClinicProyect.MedicalClinic.controller;

import com.medicalClinicProyect.MedicalClinic.dto.ShowAdministrator;
import com.medicalClinicProyect.MedicalClinic.dto.ShowShift;
import com.medicalClinicProyect.MedicalClinic.service.ShiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shift")
public class ShiftController {

    private final ShiftService shiftService;


    @GetMapping
    public ResponseEntity<List<ShowShift>> findAllShifts(@PageableDefault(size = 5) Pageable pageable){

        List<ShowShift> response = shiftService.findAll(pageable);
        return ResponseEntity.ok(response);
    }
}
