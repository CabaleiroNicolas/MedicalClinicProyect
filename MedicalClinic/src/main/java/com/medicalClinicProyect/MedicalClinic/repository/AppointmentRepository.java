package com.medicalClinicProyect.MedicalClinic.repository;

import com.medicalClinicProyect.MedicalClinic.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
}
