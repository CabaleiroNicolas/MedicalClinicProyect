package com.medicalClinicProyect.MedicalClinic.service.impl;

import com.medicalClinicProyect.MedicalClinic.dto.ShowReport;
import com.medicalClinicProyect.MedicalClinic.entity.Appointment;
import com.medicalClinicProyect.MedicalClinic.entity.Report;
import com.medicalClinicProyect.MedicalClinic.exception.ResourceNotFoundException;
import com.medicalClinicProyect.MedicalClinic.repository.ReportRepository;
import com.medicalClinicProyect.MedicalClinic.service.ReportService;
import com.medicalClinicProyect.MedicalClinic.util.UtilityMethods;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    @Override
    public void generateReport(Report report) {
        reportRepository.save(report);
    }

    @Override
    public List<ShowReport> findAllMyReports(Pageable pageable) {

        String username = UtilityMethods.getAuthenticatedUsername();

        Page<Report> reportsPage = reportRepository.findByAppointmentProfessionalUsername(username,pageable);
        List<Report> reportsList = new ArrayList<>();
        reportsPage.forEach(reportsList::add);

        return generateShowReportList(reportsList);
    }

    @Override
    public List<ShowReport> findAllReports() {
        return generateShowReportList(reportRepository.findAll());
    }

    private static List<ShowReport> generateShowReportList(List<Report> reportsPage) {

        List<ShowReport> list = new ArrayList<>();
        reportsPage.forEach(report -> {
            ShowReport showReport = generateShowReport(report);
            list.add(showReport);
        });

        return list;
    }

    private static ShowReport generateShowReport(Report report) {
        ShowReport showReport = new ShowReport();
        Appointment appointment = report.getAppointment();
        showReport.setAppointmentId(appointment.getId());
        showReport.setProfessional(appointment.getProfessional().getLastname()+" "+appointment.getProfessional().getName());
        showReport.setPatient(appointment.getPatient().getLastname()+" "+appointment.getPatient().getName());
        showReport.setObservations(report.getObservations());
        showReport.setAppointmentDate(appointment.getAppointmentDate());
        showReport.setReportDate(report.getIssueAt());
        return showReport;
    }

}
