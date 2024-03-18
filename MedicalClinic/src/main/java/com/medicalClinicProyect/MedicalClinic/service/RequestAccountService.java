package com.medicalClinicProyect.MedicalClinic.service;

import com.medicalClinicProyect.MedicalClinic.entity.RequestAccount;

public interface RequestAccountService {
    RequestAccount findRequestAccountByProfessional(Long id);

    void saveRequestAccount(RequestAccount request);

    void deleteRequestAccountByProfessional(Long id);
}
