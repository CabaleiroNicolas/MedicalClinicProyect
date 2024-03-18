package com.medicalClinicProyect.MedicalClinic.repository;

import com.medicalClinicProyect.MedicalClinic.entity.GrantedAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrantedAuthorityRepository extends JpaRepository<GrantedAuthority,Long> {
}
