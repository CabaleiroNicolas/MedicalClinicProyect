package com.medicalClinicProyect.MedicalClinic.util;

import com.medicalClinicProyect.MedicalClinic.util.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@MappedSuperclass
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private String status = "Unread";
    private LocalDateTime issueAt = LocalDateTime.now();

}
