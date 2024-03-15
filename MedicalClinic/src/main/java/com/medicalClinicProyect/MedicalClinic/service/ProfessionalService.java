package com.medicalClinicProyect.MedicalClinic.service;

import com.medicalClinicProyect.MedicalClinic.dto.RegisterProfessionalRequest;
import com.medicalClinicProyect.MedicalClinic.dto.RegisterResponse;
import com.medicalClinicProyect.MedicalClinic.entity.Professional;
import org.springframework.data.jpa.repository.Query;

public interface ProfessionalService {

    RegisterResponse register(RegisterProfessionalRequest request);
}
