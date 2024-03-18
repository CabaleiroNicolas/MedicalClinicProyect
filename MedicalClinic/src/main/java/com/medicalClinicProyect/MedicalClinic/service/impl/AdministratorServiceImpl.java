package com.medicalClinicProyect.MedicalClinic.service.impl;

import com.medicalClinicProyect.MedicalClinic.dto.AcceptOrRejectAccountResponse;
import com.medicalClinicProyect.MedicalClinic.dto.ShowAdministrator;
import com.medicalClinicProyect.MedicalClinic.entity.Administrator;
import com.medicalClinicProyect.MedicalClinic.entity.Professional;
import com.medicalClinicProyect.MedicalClinic.entity.RequestAccount;
import com.medicalClinicProyect.MedicalClinic.entity.Role;
import com.medicalClinicProyect.MedicalClinic.exception.ResourceNotFoundException;
import com.medicalClinicProyect.MedicalClinic.repository.AdministratorRepository;
import com.medicalClinicProyect.MedicalClinic.service.AdministratorService;
import com.medicalClinicProyect.MedicalClinic.service.ProfessionalService;
import com.medicalClinicProyect.MedicalClinic.service.RequestAccountService;
import com.medicalClinicProyect.MedicalClinic.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdministratorServiceImpl implements AdministratorService {

    private final AdministratorRepository administratorRepository;
    private final RequestAccountService requestAccountService;
    private final ProfessionalService professionalService;
    private final RoleService roleService;


    @Override
    public AcceptOrRejectAccountResponse acceptAccount(Long id) {

        //look for the request account specified
        RequestAccount requestAccount = requestAccountService.findRequestAccountByProfessional(id);
        //bring the professional role for after switch it
        Role roleProfessional = roleService.findRoleProfessional();

        //switch the role from 'WAITING' to 'PROFESSIONAL'
        professionalService.acceptAccount(id, roleProfessional);
        //delete the request of acceptance
        requestAccountService.deleteRequestAccountByProfessional(id);

        //create and return the response object
        return createResponseObject(id,requestAccount,"ACCEPT");
    }

    @Override
    public AcceptOrRejectAccountResponse rejectAccount(Long id) {

        //look for the request account specified
        RequestAccount requestAccount = requestAccountService.findRequestAccountByProfessional(id);

        //delete the request of acceptance
        requestAccountService.deleteRequestAccountByProfessional(id);

        //create the response object
        AcceptOrRejectAccountResponse response = createResponseObject(id, requestAccount,"REJECT");
        //delete de usr because this was rejected by administrator
        professionalService.deleteProfessionalById(id);
        return response;
    }

    @Override
    public List<ShowAdministrator> findAll(Pageable pageable) {
        Page<Administrator> page = administratorRepository.findAll(pageable);
        List<ShowAdministrator> response = new ArrayList<>();
        page.forEach(each->{
            response.add(new ShowAdministrator(each.getId(),each.getUsername()));
        });
        if(response.isEmpty()){
            throw new ResourceNotFoundException("Administrators");
        }
        return response;
    }


    private AcceptOrRejectAccountResponse createResponseObject(Long id, RequestAccount requestAccount,String status){

        //look for which admin did reject the request for show it
        String admin = SecurityContextHolder.getContext().getAuthentication().getName();
        Professional professional = professionalService.findProfessionalById(id);

        AcceptOrRejectAccountResponse response = new AcceptOrRejectAccountResponse();
        response.setMessage("Professional account was successfully "+status);
        response.setRequestId(requestAccount.getId());
        response.setDoneBy(admin);
        response.setIssueAtRequest(requestAccount.getIssueAt());
        response.setProfessionalName(professional.getName());
        response.setProfessionalLastname(professional.getLastname());
        response.setStatus(status);
        return response;
    }
}
