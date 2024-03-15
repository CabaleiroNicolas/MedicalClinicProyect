package com.medicalClinicProyect.MedicalClinic.service.impl;

import com.medicalClinicProyect.MedicalClinic.entity.Role;
import com.medicalClinicProyect.MedicalClinic.repository.RoleRepository;
import com.medicalClinicProyect.MedicalClinic.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;


    //look for a role with the name 'PATIENT' in DB
    @Override
    public Role findRolePatient() {
       return roleRepository.findRolePatient();
    }

    //look for a role with the name 'PENDIENT' in DB
    @Override
    public Role findRolePendient() {
        return roleRepository.findRolePendient();
    }
}