package com.medicalClinicProyect.MedicalClinic.service.impl;

import com.medicalClinicProyect.MedicalClinic.entity.PatientNotification;
import com.medicalClinicProyect.MedicalClinic.entity.ProfessionalNotification;
import com.medicalClinicProyect.MedicalClinic.repository.PatientNotificationRepository;
import com.medicalClinicProyect.MedicalClinic.repository.ProfessionalNotificationRepository;
import com.medicalClinicProyect.MedicalClinic.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final PatientNotificationRepository patientNotificationRepository;
    private final ProfessionalNotificationRepository professionalNotificationRepository;

    @Override
    public void sendNotificationToPatient(PatientNotification notification) {
        patientNotificationRepository.save(notification);
    }

    @Override
    public void sendNotificationToProfessional(ProfessionalNotification notification) {
        professionalNotificationRepository.save(notification);
    }
}
