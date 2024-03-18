package com.medicalClinicProyect.MedicalClinic.service.impl;

import com.medicalClinicProyect.MedicalClinic.entity.Role;
import com.medicalClinicProyect.MedicalClinic.exception.ResourceNotFoundException;
import com.medicalClinicProyect.MedicalClinic.repository.RoleRepository;
import com.medicalClinicProyect.MedicalClinic.service.RoleService;
import io.jsonwebtoken.security.Jwks;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;


    //look for a role with the name 'PATIENT' in DB
    @Override
    public Role findRolePatient() {
       Optional<Role> rolePatient =  roleRepository.findRolePatient();
       if(rolePatient.isEmpty()){
            throw new ResourceNotFoundException("Role PATIENT");
        }
       return rolePatient.get();
    }

    //look for a role with the name 'PENDIENT' in DB
    @Override
    public Role findRolePendient() {
        Optional<Role> rolePendient= roleRepository.findRolePendient();
        if(rolePendient.isEmpty()){
            throw new ResourceNotFoundException("Role PENDIENT");
        }
        return rolePendient.get();
    }

    //look for a role with the name 'PROFESSIONAL' in DB
    @Override
    public Role findRoleProfessional() {
        Optional<Role> roleProfessional = roleRepository.findRoleProfessional();
        if(roleProfessional.isEmpty()){
            throw new ResourceNotFoundException("Role PROFESSIONAL");
        }
        return roleProfessional.get();
    }

    @Override
    public Role findRoleByName(String roleName) {

        Optional<Role> role = roleRepository.findByName(roleName);
        if(role.isEmpty()){
            throw new ResourceNotFoundException("Role");
        }
        return role.get();
    }

}
