package com.medicalClinicProyect.MedicalClinic.repository;

import com.medicalClinicProyect.MedicalClinic.entity.RequestAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RequestAccountRepository extends JpaRepository<RequestAccount,Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM RequestAccount r WHERE r.applicant.id = :id")
    void deleteByProfessional(Long id);


    @Query("SELECT r FROM RequestAccount r WHERE r.applicant.id = :id")
    Optional<RequestAccount> findByProfessional(Long id);

}
