package com.medicalClinicProyect.MedicalClinic.repository;

import com.medicalClinicProyect.MedicalClinic.entity.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SpecialityRepository extends JpaRepository<Speciality,Long> {


    @Query("SELECT s FROM Speciality s WHERE s.name = :speciality")
    Speciality findByName(String speciality);
}
