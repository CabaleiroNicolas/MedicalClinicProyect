package com.medicalClinicProyect.MedicalClinic.security;

import com.medicalClinicProyect.MedicalClinic.entity.Administrator;
import com.medicalClinicProyect.MedicalClinic.entity.Patient;
import com.medicalClinicProyect.MedicalClinic.entity.Professional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

//    private final AdministratorRepository administratorRepository;
//    private final PatientRepository patientRepository;
//    private final ProfessionalRepository professionalRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        Administrator admin = administratorRepository.findByUsername(username);
//        if (admin != null) {
//            return User.withUsername(admin.getUsername())
//                    .password(admin.getPassword())
//                    .roles("ADMIN")
//                    .build();
//        }
//
//        Patient patient = patientRepository.findByUsername(username);
//        if (patient != null) {
//            return User.withUsername(patient.getUsername())
//                    .password(patient.getPassword())
//                    .roles("PATIENT")
//                    .build();
//        }
//
//        Professional professional = professionalRepository.findByUsername(username);
//        if (professional != null) {
//            return User.withUsername(professional.getUsername())
//                    .password(professional.getPassword())
//                    .roles("PROFESSIONAL")
//                    .build();
//        }
//
//        throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        return null;
    }

}
