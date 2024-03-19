package com.medicalClinicProyect.MedicalClinic.service;

import com.medicalClinicProyect.MedicalClinic.dto.*;
import com.medicalClinicProyect.MedicalClinic.entity.Patient;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PatientService {
    RegisterResponse register(RegisterPatientRequest request);

    List<ShowPatient> findAll(Pageable pageable);

    Patient findPatientById(Long id);

    void updateProfile(Long id,UpdatePatientRequest update);
}
