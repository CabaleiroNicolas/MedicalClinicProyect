package com.medicalClinicProyect.MedicalClinic.repository;

import com.medicalClinicProyect.MedicalClinic.entity.PatientNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientNotificationRepository extends JpaRepository<PatientNotification,Long> {
}
