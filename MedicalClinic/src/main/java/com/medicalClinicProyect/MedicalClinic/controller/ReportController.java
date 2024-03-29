package com.medicalClinicProyect.MedicalClinic.controller;

import com.medicalClinicProyect.MedicalClinic.dto.showDto.ShowReport;
import com.medicalClinicProyect.MedicalClinic.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<List<ShowReport>> findProfessionalReports(Pageable pageable){
        List<ShowReport> response = reportService.findAllMyReports(pageable);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/all")
    public ResponseEntity<List<ShowReport>> findAllReports(){

        List<ShowReport> response = reportService.findAllReports();
        return ResponseEntity.ok(response);

    }

}
