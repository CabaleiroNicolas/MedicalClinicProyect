package com.medicalClinicProyect.MedicalClinic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="shifts")
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "professional_id", nullable = false)
    private Professional professional;

    @ManyToOne @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    //to create class dateShift
    @Column(nullable = false)
    private String dateShift;
}
