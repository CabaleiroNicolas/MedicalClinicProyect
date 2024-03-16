package com.medicalClinicProyect.MedicalClinic.entity;


import com.medicalClinicProyect.MedicalClinic.util.StatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

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
    private Date issueAt = new Date();

}
