package com.medicalClinicProyect.MedicalClinic.service;

import com.medicalClinicProyect.MedicalClinic.dto.requestDto.ChangePasswordRequest;
import com.medicalClinicProyect.MedicalClinic.dto.requestDto.RegisterProfessionalRequest;
import com.medicalClinicProyect.MedicalClinic.dto.requestDto.UpdateProfileRequest;
import com.medicalClinicProyect.MedicalClinic.dto.responseDto.RegisterResponse;
import com.medicalClinicProyect.MedicalClinic.dto.showDto.ShowProfessional;
import com.medicalClinicProyect.MedicalClinic.entity.Professional;
import com.medicalClinicProyect.MedicalClinic.entity.Role;
import org.springframework.data.domain.Pageable;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public interface ProfessionalService {

    RegisterResponse register(RegisterProfessionalRequest request) throws SQLIntegrityConstraintViolationException;

    List<ShowProfessional> findAll(Pageable pageable);

    Professional findProfessionalByUsername(String username);

    List<ShowProfessional> findAcceptedProfessionals(Pageable pageable);

    List<ShowProfessional> findPendientProfessionals(Pageable pageable);

    void acceptAccount(Long id, Role roleProfessional);

    void deleteProfessionalById(Long id);

    Professional findProfessionalById(Long id);

    void updateProfile(String username, UpdateProfileRequest update);

    void changePassword(String username, ChangePasswordRequest request);

    void save(Professional user);
}
