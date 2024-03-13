package com.medicalClinicProyect.MedicalClinic.entity;

import com.medicalClinicProyect.MedicalClinic.util.RoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="role")
    private List<GrantedAuthority> grantedAuthorities;

}
