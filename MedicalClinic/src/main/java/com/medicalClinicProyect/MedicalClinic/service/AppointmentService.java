package com.medicalClinicProyect.MedicalClinic.service;

import com.medicalClinicProyect.MedicalClinic.dto.RegisterAppointmentRequest;
import com.medicalClinicProyect.MedicalClinic.dto.ShowAppointment;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AppointmentService {
    List<ShowAppointment> findAll(Pageable pageable);

    void addAppointment(RegisterAppointmentRequest request);
}