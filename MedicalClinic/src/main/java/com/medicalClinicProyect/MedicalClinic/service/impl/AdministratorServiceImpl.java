package com.medicalClinicProyect.MedicalClinic.service.impl;

import com.medicalClinicProyect.MedicalClinic.dto.AcceptOrRejectAccountResponse;
import com.medicalClinicProyect.MedicalClinic.entity.Administrator;
import com.medicalClinicProyect.MedicalClinic.entity.Professional;
import com.medicalClinicProyect.MedicalClinic.entity.RequestAccount;
import com.medicalClinicProyect.MedicalClinic.entity.Role;
import com.medicalClinicProyect.MedicalClinic.exception.ResourceNotFoundException;
import com.medicalClinicProyect.MedicalClinic.repository.AdministratorRepository;
import com.medicalClinicProyect.MedicalClinic.repository.ProfessionalRepository;
import com.medicalClinicProyect.MedicalClinic.repository.RequestAccountRepository;
import com.medicalClinicProyect.MedicalClinic.repository.RoleRepository;
import com.medicalClinicProyect.MedicalClinic.service.AdministratorService;
import com.medicalClinicProyect.MedicalClinic.service.ProfessionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdministratorServiceImpl implements AdministratorService {

    private final AdministratorRepository administratorRepository;
    private final RequestAccountRepository requestAccountRepository;
    private final ProfessionalRepository professionalRepository;
    private final RoleRepository roleRepository;


    //This method is in charge of to add a request account into DB to be accepted in the future by an Administrator
    @Override
    public void addRequestAccount(RequestAccount request) {
        requestAccountRepository.save(request);
    }

    @Override
    public AcceptOrRejectAccountResponse acceptAccount(Long id) {

        String admin = SecurityContextHolder.getContext().getAuthentication().getName();


        RequestAccount requestAccount = requestAccountRepository.findByProfessional(id);
        Role roleProfessional = roleRepository.findRoleProfessional();
        Optional<Professional> professional = professionalRepository.findById(id);

        if(requestAccount==null){
            throw new ResourceNotFoundException("Request account");
        }
        professionalRepository.accept(id, roleProfessional);
        requestAccountRepository.deleteByProfessional(id);


        AcceptOrRejectAccountResponse response = new AcceptOrRejectAccountResponse();
        response.setMessage("Professional account was successfully accept!");
        response.setRequestId(requestAccount.getId());
        response.setBy(admin);
        response.setIssueAtRequest(requestAccount.getIssueAt());
        response.setProfessionalName(professional.get().getName());
        response.setProfessionalLastname(professional.get().getLastname());
        response.setStatus("ACCEPT");
        return response;
    }
}
