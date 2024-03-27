package com.medicalClinicProyect.MedicalClinic.service;

import com.medicalClinicProyect.MedicalClinic.dto.ReportRequest;
import com.medicalClinicProyect.MedicalClinic.dto.ShowReport;
import com.medicalClinicProyect.MedicalClinic.entity.Report;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReportService {
    void generateReport(Report report);

    List<ShowReport> findAllMyReports(Pageable pageable);

    List<ShowReport> findAllReports();

}
