package com.medicalClinicProyect.MedicalClinic.service;

import com.medicalClinicProyect.MedicalClinic.dto.responseDto.AcceptOrRejectAccountResponse;
import com.medicalClinicProyect.MedicalClinic.dto.showDto.ShowAdministrator;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdministratorService {

    AcceptOrRejectAccountResponse acceptAccount(Long id);

    AcceptOrRejectAccountResponse rejectAccount(Long id);

    List<ShowAdministrator> findAll(Pageable pageable);
}
