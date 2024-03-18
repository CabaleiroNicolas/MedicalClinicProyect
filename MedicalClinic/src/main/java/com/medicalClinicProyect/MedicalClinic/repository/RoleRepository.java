package com.medicalClinicProyect.MedicalClinic.repository;

import com.medicalClinicProyect.MedicalClinic.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    @Query("SELECT r FROM Role r WHERE r.name = 'PATIENT'")
    Optional<Role> findRolePatient();

    @Query("SELECT r FROM Role r WHERE r.name = 'PENDIENT'")
    Optional<Role> findRolePendient();

    @Query("SELECT r FROM Role r WHERE r.name = 'PROFESSIONAL'")
    Optional<Role> findRoleProfessional();

    Optional<Role> findByName(String roleName);
}
