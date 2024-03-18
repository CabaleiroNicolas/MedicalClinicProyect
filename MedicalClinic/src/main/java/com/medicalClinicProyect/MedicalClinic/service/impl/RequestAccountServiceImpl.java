package com.medicalClinicProyect.MedicalClinic.service.impl;

import com.medicalClinicProyect.MedicalClinic.entity.Professional;
import com.medicalClinicProyect.MedicalClinic.entity.RequestAccount;
import com.medicalClinicProyect.MedicalClinic.exception.ResourceNotFoundException;
import com.medicalClinicProyect.MedicalClinic.repository.RequestAccountRepository;
import com.medicalClinicProyect.MedicalClinic.service.RequestAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestAccountServiceImpl implements RequestAccountService {

    private final RequestAccountRepository requestAccountRepository;

    @Override
    public RequestAccount findRequestAccountByProfessional(Long id) {

        Optional<RequestAccount> requestAccount = requestAccountRepository.findByProfessional(id);
        if(requestAccount.isEmpty()){
            throw new ResourceNotFoundException("Request of acceptance");
        }
        return requestAccount.get();
    }

    //This method is in charge of to add a request account into DB to be accepted in the future by an Administrator
    @Override
    public void saveRequestAccount(RequestAccount request) {
        requestAccountRepository.save(request);
    }

    @Override
    public void deleteRequestAccountByProfessional(Long id) {
        requestAccountRepository.deleteByProfessional(id);
    }
}
