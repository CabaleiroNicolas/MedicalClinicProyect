package com.medicalClinicProyect.MedicalClinic.service;

import com.medicalClinicProyect.MedicalClinic.dto.CancelAppointmentRequest;
import com.medicalClinicProyect.MedicalClinic.dto.RegisterAppointmentRequest;
import com.medicalClinicProyect.MedicalClinic.dto.ShowAppointment;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {
    List<ShowAppointment> findAll(Pageable pageable);

    void addAppointment(RegisterAppointmentRequest request);

    List<ShowAppointment> findAllByUser(Pageable pageable);

    List<ShowAppointment> findAvailableAppointments(Pageable pageable);

    ShowAppointment bookAppointment(Long appointmentId);

    List<ShowAppointment> findAllPending(Pageable pageable, String day);

    ShowAppointment cancelAppointment(Long appointmentId, CancelAppointmentRequest cancelRequest);
}
