package com.medicalClinicProyect.MedicalClinic.service;

import com.medicalClinicProyect.MedicalClinic.dto.*;
import com.medicalClinicProyect.MedicalClinic.entity.Patient;
import org.springframework.data.domain.Pageable;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public interface PatientService {
    RegisterResponse register(RegisterPatientRequest request) throws SQLIntegrityConstraintViolationException;

    List<ShowPatient> findAll(Pageable pageable);

    Patient findPatientById(Long id);

    Patient findPatientByUsername(String username);

    void updateProfile(String username,UpdateProfileRequest update);

    void changePassword(ChangePasswordRequest request);
}
