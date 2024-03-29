package com.medicalClinicProyect.MedicalClinic.service;

import com.medicalClinicProyect.MedicalClinic.dto.requestDto.ChangePasswordRequest;
import com.medicalClinicProyect.MedicalClinic.dto.requestDto.RegisterPatientRequest;
import com.medicalClinicProyect.MedicalClinic.dto.requestDto.UpdateProfileRequest;
import com.medicalClinicProyect.MedicalClinic.dto.responseDto.RegisterResponse;
import com.medicalClinicProyect.MedicalClinic.dto.showDto.ShowPatient;
import com.medicalClinicProyect.MedicalClinic.entity.Patient;
import org.springframework.data.domain.Pageable;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public interface PatientService {
    RegisterResponse register(RegisterPatientRequest request) throws SQLIntegrityConstraintViolationException;

    List<ShowPatient> findAll(Pageable pageable);

    Patient findPatientById(Long id);

    Patient findPatientByUsername(String username);

    void updateProfile(String username, UpdateProfileRequest update);

    void changePassword(String username, ChangePasswordRequest request);

    void save(Patient patient);

}
