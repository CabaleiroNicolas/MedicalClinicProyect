package com.medicalClinicProyect.MedicalClinic.service.impl;

import com.medicalClinicProyect.MedicalClinic.dto.RegisterProfessionalRequest;
import com.medicalClinicProyect.MedicalClinic.dto.RegisterResponse;
import com.medicalClinicProyect.MedicalClinic.entity.RequestAccount;
import com.medicalClinicProyect.MedicalClinic.entity.Professional;
import com.medicalClinicProyect.MedicalClinic.entity.Role;
import com.medicalClinicProyect.MedicalClinic.entity.Speciality;
import com.medicalClinicProyect.MedicalClinic.repository.ProfessionalRepository;
import com.medicalClinicProyect.MedicalClinic.repository.SpecialityRepository;
import com.medicalClinicProyect.MedicalClinic.security.JwtService;
import com.medicalClinicProyect.MedicalClinic.service.AdministratorService;
import com.medicalClinicProyect.MedicalClinic.service.ProfessionalService;
import com.medicalClinicProyect.MedicalClinic.service.RoleService;
import com.medicalClinicProyect.MedicalClinic.util.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProfessionalServiceImpl implements ProfessionalService {

    private final ProfessionalRepository professionalRepository;
    private final AdministratorService administratorService;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;
    private final RoleService roleService;
    private final SpecialityRepository specialityRepository;

    @Override
    public RegisterResponse register(RegisterProfessionalRequest request) {

        String password = request.getPassword();
        String passwordRepeat = request.getConfirmPassword();

        if (!passwordRepeat.equals(password)) {
            throw new IllegalArgumentException();
        }

        String passwordEncode = encoder.encode(password);
        Role role = roleService.findRolePendient();
        Speciality speciality = specialityRepository.findByName(request.getSpeciality());
        Professional professional = getProfessional(request, passwordEncode, role, speciality);
        professionalRepository.save(professional);

        RequestAccount requestAccount = new RequestAccount();
        requestAccount.setApplicant(professional);
        requestAccount.setStatus(StatusEnum.WAITING);
        administratorService.addRequestAccount(requestAccount);

        RegisterResponse response = new RegisterResponse();
        String jwt = jwtService.generateToken(generateExtraClaims(professional,role),professional);

        response.setUsername(professional.getUsername());
        response.setName(professional.getName());
        response.setLastName(professional.getLastname());
        response.setJwt(jwt);
        response.setIssueAt(new Date(System.currentTimeMillis()));
        response.setMessage("Professional Registered Successfully, must wait for approval an administrator for to use this account");
        return response;
    }

    private static Professional getProfessional(RegisterProfessionalRequest request, String passwordEncode, Role role, Speciality speciality) {

        String photo = request.getProfilePhoto();

        Professional professional = new Professional();
        professional.setUsername(request.getUsername());
        professional.setPassword(passwordEncode);
        professional.setName(request.getName());
        professional.setLastname(request.getLastname());
        professional.setContactNumber(request.getContactNumber());
        professional.setSpeciality(speciality);
        professional.setRole(role);
        if(photo != null){
            professional.setProfilePhoto(photo);
        }
        return professional;
    }

    private static Map<String, Object> generateExtraClaims(Professional professional, Role role){

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("Role", role.getName());
        extraClaims.put("Authorities", professional.getAuthorities());

        return extraClaims;
    }

}
