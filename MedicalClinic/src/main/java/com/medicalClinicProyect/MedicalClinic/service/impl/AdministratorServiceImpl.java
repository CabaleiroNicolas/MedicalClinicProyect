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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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

        //look for the request account specified
        RequestAccount requestAccount = requestAccountRepository.findByProfessional(id);
        //bring the professional role for after switch it
        Role roleProfessional = roleRepository.findRoleProfessional();

        if(requestAccount==null){
            throw new ResourceNotFoundException("Request account");
        }

        //switch the role from 'WAITING' to 'PROFESSIONAL'
        professionalRepository.accept(id, roleProfessional);
        //delete the request of acceptance
        requestAccountRepository.deleteByProfessional(id);

        //create and return the response object
        return createResponseObject(id,requestAccount,"ACCEPT");
    }

    @Override
    public AcceptOrRejectAccountResponse rejectAccount(Long id) {

        //look for the request account specified
        RequestAccount requestAccount = requestAccountRepository.findByProfessional(id);

        if(requestAccount==null){
            throw new ResourceNotFoundException("Request account");
        }

        //delete the request of acceptance
        requestAccountRepository.deleteByProfessional(id);

        //create the response object
        AcceptOrRejectAccountResponse response = createResponseObject(id, requestAccount,"REJECT");
        //delete de usr because this was rejected by administrator
        professionalRepository.deleteById(id);
        return response;
    }

    private AcceptOrRejectAccountResponse createResponseObject(Long id, RequestAccount requestAccount,String status){

        //look for which admin did reject the request for show it
        String admin = SecurityContextHolder.getContext().getAuthentication().getName();
        Professional professional = professionalRepository.findById(id).get();

        AcceptOrRejectAccountResponse response = new AcceptOrRejectAccountResponse();
        response.setMessage("Professional account was successfully "+status);
        response.setRequestId(requestAccount.getId());
        response.setAcceptBy(admin);
        response.setIssueAtRequest(requestAccount.getIssueAt());
        response.setProfessionalName(professional.getName());
        response.setProfessionalLastname(professional.getLastname());
        response.setStatus(status);
        return response;
    }
}
