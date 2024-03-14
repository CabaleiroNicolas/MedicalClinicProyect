package com.medicalClinicProyect.MedicalClinic.service;

import com.medicalClinicProyect.MedicalClinic.dto.RegisterPatientRequest;
import com.medicalClinicProyect.MedicalClinic.dto.RegisterResponse;

public interface PatientService {
    RegisterResponse register(RegisterPatientRequest request);
}
