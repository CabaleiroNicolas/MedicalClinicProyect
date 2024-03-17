package com.medicalClinicProyect.MedicalClinic.service;

import com.medicalClinicProyect.MedicalClinic.dto.RegisterPatientRequest;
import com.medicalClinicProyect.MedicalClinic.dto.RegisterResponse;
import com.medicalClinicProyect.MedicalClinic.dto.ShowPatient;
import com.medicalClinicProyect.MedicalClinic.dto.ShowProfessional;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PatientService {
    RegisterResponse register(RegisterPatientRequest request);

    List<ShowPatient> findAll(Pageable pageable);
}
