package com.medicalClinicProyect.MedicalClinic.entity;

import com.medicalClinicProyect.MedicalClinic.util.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="professionals")
public class Professional extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String lastname;
    private String profilePhoto;
    @Column(nullable = false)
    private String contactNumber;
    @ManyToOne
    private Speciality speciality;
    @OneToMany(mappedBy = "professional")
    private List<Appointment> appointments;
    private Long CanceledAppointments = 0L;

}
