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
    @ManyToOne @JoinColumn(name = "professional_id")
    @Column(nullable = false)
    private Professional professional;
    @Column(nullable = false)
    @ManyToOne @JoinColumn(name = "patient_id")
    private Patient patient;
    //to create class dateShift
    @Column(nullable = false)
    private String dateShift;
}
