package com.medicalClinicProyect.MedicalClinic.service;

import com.medicalClinicProyect.MedicalClinic.dto.AcceptOrRejectAccountResponse;
import com.medicalClinicProyect.MedicalClinic.entity.RequestAccount;

public interface AdministratorService {

    void addRequestAccount(RequestAccount request);
    AcceptOrRejectAccountResponse acceptAccount(Long id);
}
