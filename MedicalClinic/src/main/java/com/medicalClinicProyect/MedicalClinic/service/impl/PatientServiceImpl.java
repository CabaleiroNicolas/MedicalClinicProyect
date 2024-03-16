package com.medicalClinicProyect.MedicalClinic.service.impl;

import com.medicalClinicProyect.MedicalClinic.dto.RegisterPatientRequest;
import com.medicalClinicProyect.MedicalClinic.dto.RegisterResponse;
import com.medicalClinicProyect.MedicalClinic.entity.Patient;
import com.medicalClinicProyect.MedicalClinic.entity.Role;
import com.medicalClinicProyect.MedicalClinic.repository.PatientRepository;
import com.medicalClinicProyect.MedicalClinic.security.JwtService;
import com.medicalClinicProyect.MedicalClinic.service.PatientService;
import com.medicalClinicProyect.MedicalClinic.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;
    private final RoleService roleService;


    //This method is in charge of register a new patient into the system
    @Override
    public RegisterResponse register(RegisterPatientRequest request) {

        //validate passwords matches
        String password = request.getPassword();
        String passwordRepeat = request.getConfirmPassword();
        if (!passwordRepeat.equals(password)) {
            throw new IllegalArgumentException();
        }

        //Encode password
        String passwordEncode = encoder.encode(password);

        //Assign role PATIENT
        Role role = roleService.findRolePatient();

        //create an object Patient and to save into DB
        Patient patient = getPatient(request, passwordEncode, role);
        patientRepository.save(patient);

        //generate authentication token JWT to be included into response
        String jwt = jwtService.generateToken(generateExtraClaims(patient,role),patient);

        //generate response object
        RegisterResponse response = new RegisterResponse();
        response.setUsername(patient.getUsername());
        response.setName(patient.getName());
        response.setLastName(patient.getLastname());
        response.setJwt(jwt);
        response.setIssueAt(new Date(System.currentTimeMillis()));
        response.setMessage("Patient Registered Successfully");
        return response;
    }

    //generate a Patient object with the data required
    private static Patient getPatient(RegisterPatientRequest request, String passwordEncode, Role role) {

        String photo = request.getProfilePhoto();

        Patient patient = new Patient();
        patient.setUsername(request.getUsername());
        patient.setPassword(passwordEncode);
        patient.setName(request.getName());
        patient.setLastname(request.getLastname());
        patient.setAge(request.getAge());
        patient.setAddress(request.getAddress());
        patient.setContactNumber(request.getContactNumber());
        patient.setDniNumber(request.getDniNumber());
        patient.setRole(role);
        if(photo != null){
            patient.setProfilePhoto(photo);
        }
        return patient;
    }

    //generate the extra claims to be placed into jwt
    private static Map<String, Object> generateExtraClaims(Patient patient,Role role){

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("Role", role.getName());
        extraClaims.put("authorities", patient.getAuthorities().stream().map((GrantedAuthority::getAuthority)).collect(Collectors.toList()));

        return extraClaims;
    }
}
