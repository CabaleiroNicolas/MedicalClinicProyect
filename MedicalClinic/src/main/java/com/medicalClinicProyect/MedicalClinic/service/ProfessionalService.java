package com.medicalClinicProyect.MedicalClinic.service;

import com.medicalClinicProyect.MedicalClinic.dto.RegisterProfessionalRequest;
import com.medicalClinicProyect.MedicalClinic.dto.RegisterResponse;
import com.medicalClinicProyect.MedicalClinic.dto.ShowProfessional;
import com.medicalClinicProyect.MedicalClinic.entity.Professional;
import com.medicalClinicProyect.MedicalClinic.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProfessionalService {

    RegisterResponse register(RegisterProfessionalRequest request);

    List<ShowProfessional> findAll(Pageable pageable);

    List<ShowProfessional> findAcceptedProfessionals(Pageable pageable);

    List<ShowProfessional> findPendientProfessionals(Pageable pageable);

    void acceptAccount(Long id, Role roleProfessional);

    void deleteProfessionalById(Long id);

    Professional findProfessionalById(Long id);
}
