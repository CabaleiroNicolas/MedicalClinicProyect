package com.medicalClinicProyect.MedicalClinic.entity;

import com.medicalClinicProyect.MedicalClinic.util.Notification;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ProfessionalNotification extends Notification {

    @ManyToOne
    private Professional professional;
}
