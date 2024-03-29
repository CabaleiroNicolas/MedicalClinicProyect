package com.medicalClinicProyect.MedicalClinic.controller;

import com.medicalClinicProyect.MedicalClinic.dto.requestDto.ChangePasswordRequest;
import com.medicalClinicProyect.MedicalClinic.dto.requestDto.UpdateProfileRequest;
import com.medicalClinicProyect.MedicalClinic.entity.Patient;
import com.medicalClinicProyect.MedicalClinic.entity.Professional;
import com.medicalClinicProyect.MedicalClinic.service.impl.CustomUserDetailsService;
import com.medicalClinicProyect.MedicalClinic.service.PatientService;
import com.medicalClinicProyect.MedicalClinic.service.ProfessionalService;
import com.medicalClinicProyect.MedicalClinic.util.User;
import com.medicalClinicProyect.MedicalClinic.util.UtilityMethods;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

        //get the authenticated user's username
        String username = UtilityMethods.getAuthenticatedUsername();
        User user = (User)userDetailsService.loadUserByUsername(username);

        //verify what instance the user belongs and update your profile with the pertinent service
        if(user instanceof Professional) professionalService.updateProfile(username, request);
        if(user instanceof Patient) patientService.updateProfile(username,request);

        return ResponseEntity.ok("Data Updated Successfully");
    }

    @PutMapping("/update/password")
    public ResponseEntity<String> changePassword(@RequestBody @Valid ChangePasswordRequest request){

        //get the authenticated user's username
        String username = UtilityMethods.getAuthenticatedUsername();
        User user = (User)userDetailsService.loadUserByUsername(username);

        //verify what instance the user belongs and update your password with the pertinent service
        if(user instanceof Professional) professionalService.changePassword(username, request);
        if(user instanceof Patient) patientService.changePassword(username,request);

        return ResponseEntity.ok("Password Updated Successfully");
    }


}
