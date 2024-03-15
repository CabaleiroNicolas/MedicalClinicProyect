package com.medicalClinicProyect.MedicalClinic.entity;

import com.medicalClinicProyect.MedicalClinic.util.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="patients")
public class Patient extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String lastname;
    @Column(nullable = false)
    private Long age;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String dniNumber;
    @Column(nullable = false)
    private String contactNumber;
    private String profilePhoto;

    @OneToMany(mappedBy = "patient")
    private List<Shift> shifts;


}
