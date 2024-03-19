package com.medicalClinicProyect.MedicalClinic.controller;

import com.medicalClinicProyect.MedicalClinic.dto.UpdateProfileRequest;
import com.medicalClinicProyect.MedicalClinic.entity.Patient;
import com.medicalClinicProyect.MedicalClinic.entity.Professional;
import com.medicalClinicProyect.MedicalClinic.security.CustomUserDetailsService;
import com.medicalClinicProyect.MedicalClinic.service.PatientService;
import com.medicalClinicProyect.MedicalClinic.service.ProfessionalService;
import com.medicalClinicProyect.MedicalClinic.util.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final PatientService patientService;
    private final ProfessionalService professionalService;
    private final CustomUserDetailsService userDetailsService;

    @PutMapping("/update")
    public ResponseEntity<String> updateProfile(@RequestBody UpdateProfileRequest request){

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(username);
        System.out.println(request.getAddress());
        System.out.println(request.getContactNumber());
        User user = (User)userDetailsService.loadUserByUsername(username);

        if(user instanceof Professional) professionalService.updateProfile(username, request);

        if(user instanceof Patient) patientService.updateProfile(username,request);

        return ResponseEntity.ok("Data Updated Successfully");
    }
}
