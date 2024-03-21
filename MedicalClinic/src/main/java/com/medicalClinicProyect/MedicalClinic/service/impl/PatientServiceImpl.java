package com.medicalClinicProyect.MedicalClinic.service.impl;

import com.medicalClinicProyect.MedicalClinic.dto.*;
import com.medicalClinicProyect.MedicalClinic.entity.Patient;
import com.medicalClinicProyect.MedicalClinic.entity.Professional;
import com.medicalClinicProyect.MedicalClinic.entity.Role;
import com.medicalClinicProyect.MedicalClinic.exception.PasswordNotMatchesException;
import com.medicalClinicProyect.MedicalClinic.exception.ResourceNotFoundException;
import com.medicalClinicProyect.MedicalClinic.exception.WrongAccountRequestException;
import com.medicalClinicProyect.MedicalClinic.repository.PatientRepository;
import com.medicalClinicProyect.MedicalClinic.security.CustomUserDetailsService;
import com.medicalClinicProyect.MedicalClinic.security.JwtService;
import com.medicalClinicProyect.MedicalClinic.service.PatientService;
import com.medicalClinicProyect.MedicalClinic.service.RoleService;
import com.medicalClinicProyect.MedicalClinic.util.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;
    private final RoleService roleService;


    //This method is in charge of register a new patient into the system
    @Override
    public RegisterResponse register(RegisterPatientRequest request) throws SQLIntegrityConstraintViolationException {


        //if username already exist, it cannot to be registered
        String username = request.getUsername();
        User user = (User)userDetailsService.loadUserByUsernameRegister(username);
        if(user != null){
            throw new SQLIntegrityConstraintViolationException();
        }

        //validate passwords matches
        String password = request.getPassword();
        String passwordRepeat = request.getConfirmPassword();
        if(!passwordRepeat.equals(password)) {
            throw new PasswordNotMatchesException();
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
        response.setMessage("Patient Registered Successfully");
        return response;
    }

    @Override
    public List<ShowPatient> findAll(Pageable pageable) {

        Page<Patient> page = patientRepository.findAll(pageable);
        return getShowPatient(page);
    }

    @Override
    public Patient findPatientByUsername(String username) {
        Optional<Patient> patient = patientRepository.findByUsername(username);
        if(patient.isEmpty()){
            throw new ResourceNotFoundException("Account");
        }
        return patient.get();

    }

    @Override
    public Patient findPatientById(Long id){
        Optional<Patient> patient = patientRepository.findById(id);
        if(patient.isEmpty()){
            throw new ResourceNotFoundException("Patient");
        }
        return patient.get();
    }

    @Override
    public void updateProfile(String username,UpdateProfileRequest update) {

        //find the user in DB
        Patient patient = findPatientByUsername(username);

        //deserialize the UpdateProfile object
        String name = update.getName();
        String lastname = update.getLastname();
        String address = update.getAddress();
        String dniNumber = update.getDniNumber();
        String contactNumber = update.getContactNumber();
        String profilePhoto = update.getProfilePhoto();

        //find what field will be updated and put in the object
        if(name != null)patient.setName(name);
        if(lastname != null)patient.setLastname(lastname);
        if(address != null)patient.setAddress(address);
        if(dniNumber != null)patient.setDniNumber(dniNumber);
        if(contactNumber != null)patient.setContactNumber(contactNumber);
        if(profilePhoto != null)patient.setProfilePhoto(profilePhoto);

        //update the user
        patientRepository.save(patient);

    }

    @Override
    public void changePassword(String username, ChangePasswordRequest request) {

        //find de user in DB
        Patient patient = findPatientByUsername(username);
        String newPassword = request.getNewPassword();

        //compare that the new password coincide with the confirmed new password and that know the old password
        if(!encoder.matches(request.getOldPassword(),patient.getPassword()) || !newPassword.equals(request.getConfirmNewPassword())){
            throw new PasswordNotMatchesException();
        }
        //encode the new password and save it in DB
        patient.setPassword(encoder.encode(newPassword));
        patientRepository.save(patient);
    }


    //generate a Patient object with the data required
    private static Patient getPatient(RegisterPatientRequest request, String passwordEncode, Role role) {

        String photo = request.getProfilePhoto();

        Patient patient = new Patient();
        patient.setUsername(request.getUsername());
        patient.setPassword(passwordEncode);
        patient.setName(request.getName());
        patient.setLastname(request.getLastname());
        patient.setAddress(request.getAddress());
        patient.setContactNumber(request.getContactNumber());
        patient.setDniNumber(request.getDniNumber());
        patient.setRole(role);
        patient.setBirthdate(LocalDate.parse(request.getBirthday(), DateTimeFormatter.ofPattern("d/M/yyyy")));
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

    private static List<ShowPatient> getShowPatient(Page<Patient> page) {
        List<ShowPatient> response = new ArrayList<>();

        page.forEach(each -> {
            ShowPatient patient = new ShowPatient();
            patient.setPatientId(each.getId());
            patient.setUsername(each.getUsername());
            patient.setName(each.getName());
            patient.setLastname(each.getLastname());
            patient.setContactNumber(each.getContactNumber());
            patient.setAge(ChronoUnit.YEARS.between(each.getBirthdate() ,LocalDate.now())+"");
            response.add(patient);
        });
        return response;
    }
}
