package com.medicalClinicProyect.MedicalClinic.repository;

import com.medicalClinicProyect.MedicalClinic.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftRepository extends JpaRepository<Shift,Long> {
}
