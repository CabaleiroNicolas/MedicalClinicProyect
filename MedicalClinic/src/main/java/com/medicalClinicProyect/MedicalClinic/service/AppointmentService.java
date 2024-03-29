package com.medicalClinicProyect.MedicalClinic.service;

import com.medicalClinicProyect.MedicalClinic.dto.requestDto.CancelAppointmentRequest;
import com.medicalClinicProyect.MedicalClinic.dto.requestDto.RegisterAppointmentRequest;
import com.medicalClinicProyect.MedicalClinic.dto.requestDto.ReportRequest;
import com.medicalClinicProyect.MedicalClinic.dto.showDto.ShowAppointment;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AppointmentService {
    List<ShowAppointment> findAll(Pageable pageable);

    void addAppointment(RegisterAppointmentRequest request);

    List<ShowAppointment> findAllByUser(Pageable pageable);

    List<ShowAppointment> findAvailableAppointments(Pageable pageable);

    ShowAppointment bookAppointment(Long appointmentId);

    List<ShowAppointment> findAllPending(Pageable pageable, String day);

    ShowAppointment cancelAppointment(Long appointmentId, CancelAppointmentRequest cancelRequest);

    void generateReport(Long appointmentId, ReportRequest request);
}
