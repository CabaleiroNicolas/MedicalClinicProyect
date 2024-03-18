package com.medicalClinicProyect.MedicalClinic.service;

import com.medicalClinicProyect.MedicalClinic.entity.Role;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleService {
    Role findRolePatient();

    Role findRolePendient();

    Role findRoleByName(String roleName);

    Role findRoleProfessional();
}
