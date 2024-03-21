package com.medicalClinicProyect.MedicalClinic.repository;

import com.medicalClinicProyect.MedicalClinic.entity.Appointment;
import com.medicalClinicProyect.MedicalClinic.entity.Professional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
    Page<Appointment> findAllByProfessionalId(Long id, Pageable pageable);

    Page<Appointment> findAllByPatientId(Long id, Pageable pageable);
}
