package com.medicalClinicProyect.MedicalClinic.entity;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "request_accounts")
public class RequestAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Professional applicant;
    private LocalDateTime issueAt = LocalDateTime.now();

}
