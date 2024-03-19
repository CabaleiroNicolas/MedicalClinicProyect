package com.medicalClinicProyect.MedicalClinic.security;

import com.medicalClinicProyect.MedicalClinic.entity.Administrator;
import com.medicalClinicProyect.MedicalClinic.entity.Patient;
import com.medicalClinicProyect.MedicalClinic.entity.Professional;
import com.medicalClinicProyect.MedicalClinic.repository.AdministratorRepository;
import com.medicalClinicProyect.MedicalClinic.repository.PatientRepository;
import com.medicalClinicProyect.MedicalClinic.repository.ProfessionalRepository;
import com.medicalClinicProyect.MedicalClinic.util.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AdministratorRepository administratorRepository;
    private final PatientRepository patientRepository;
    private final ProfessionalRepository professionalRepository;

    //This method is in charge look for a user in DB by 'username', regardless if is any role
    //because repeated usernames aren't accepted
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        User patient = getUser(username);
        if (patient != null) return patient;

        throw new UsernameNotFoundException("User not found: " + username);
    }

    public UserDetails loadUserByUsernameRegister(String username){
        return getUser(username);
    }

    private User getUser(String username) {
        Optional<Patient> patient = patientRepository.findByUsername(username);
        if (patient.isPresent()) {
            return patient.get();
        }

        Optional<Professional> professional = professionalRepository.findByUsername(username);
        if (professional.isPresent()) {
            return professional.get();
        }

        Optional<Administrator> admin = administratorRepository.findByUsername(username);
        if (admin.isPresent()) {
            return admin.get();
        }
        return null;
    }

}
