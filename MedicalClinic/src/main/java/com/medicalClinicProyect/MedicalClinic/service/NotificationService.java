package com.medicalClinicProyect.MedicalClinic.service;

import com.medicalClinicProyect.MedicalClinic.entity.PatientNotification;
import com.medicalClinicProyect.MedicalClinic.entity.ProfessionalNotification;
import com.medicalClinicProyect.MedicalClinic.util.Notification;

public interface NotificationService {
    void sendNotificationToPatient(PatientNotification notification);

    void sendNotificationToProfessional(ProfessionalNotification notification);
}
