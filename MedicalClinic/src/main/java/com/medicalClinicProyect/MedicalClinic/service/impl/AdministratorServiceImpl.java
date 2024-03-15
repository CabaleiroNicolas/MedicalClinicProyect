package com.medicalClinicProyect.MedicalClinic.service.impl;

import com.medicalClinicProyect.MedicalClinic.entity.RequestAccount;
import com.medicalClinicProyect.MedicalClinic.repository.AdministratorRepository;
import com.medicalClinicProyect.MedicalClinic.repository.RequestAccountRepository;
import com.medicalClinicProyect.MedicalClinic.service.AdministratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdministratorServiceImpl implements AdministratorService {

    private final AdministratorRepository administratorRepository;
    private final RequestAccountRepository requestAccountRepository;


    //This method is in charge of to add a request account into DB to be accepted in the future by an Administrator
    @Override
    public void addRequestAccount(RequestAccount request) {

        requestAccountRepository.save(request);
    }
}
