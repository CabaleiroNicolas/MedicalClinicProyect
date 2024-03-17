package com.medicalClinicProyect.MedicalClinic.repository;

import com.medicalClinicProyect.MedicalClinic.entity.Professional;
import com.medicalClinicProyect.MedicalClinic.entity.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ProfessionalRepository extends JpaRepository<Professional,Long> {

    Optional<Professional> findByUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE Professional p SET p.role = :role WHERE p.id = :id")
    void accept(Long id, Role role);

    Page<Professional> findAllByRoleName(String role, Pageable pageable);
}
