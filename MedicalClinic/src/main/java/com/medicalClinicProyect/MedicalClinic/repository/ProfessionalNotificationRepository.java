package com.medicalClinicProyect.MedicalClinic.repository;

import com.medicalClinicProyect.MedicalClinic.entity.ProfessionalNotification;
import com.medicalClinicProyect.MedicalClinic.util.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessionalNotificationRepository extends JpaRepository<ProfessionalNotification,Long> {
}
