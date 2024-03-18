package com.medicalClinicProyect.MedicalClinic.service.impl;

import com.medicalClinicProyect.MedicalClinic.dto.RegisterProfessionalRequest;
import com.medicalClinicProyect.MedicalClinic.dto.RegisterResponse;
import com.medicalClinicProyect.MedicalClinic.dto.ShowProfessional;
import com.medicalClinicProyect.MedicalClinic.entity.RequestAccount;
import com.medicalClinicProyect.MedicalClinic.entity.Professional;
import com.medicalClinicProyect.MedicalClinic.entity.Role;
import com.medicalClinicProyect.MedicalClinic.entity.Speciality;
import com.medicalClinicProyect.MedicalClinic.exception.PasswordNotMatchesException;
import com.medicalClinicProyect.MedicalClinic.exception.ResourceNotFoundException;
import com.medicalClinicProyect.MedicalClinic.repository.ProfessionalRepository;
import com.medicalClinicProyect.MedicalClinic.repository.SpecialityRepository;
import com.medicalClinicProyect.MedicalClinic.security.JwtService;
import com.medicalClinicProyect.MedicalClinic.service.AdministratorService;
import com.medicalClinicProyect.MedicalClinic.service.ProfessionalService;
import com.medicalClinicProyect.MedicalClinic.service.RequestAccountService;
import com.medicalClinicProyect.MedicalClinic.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfessionalServiceImpl implements ProfessionalService {

    private final ProfessionalRepository professionalRepository;
    private final RequestAccountService requestAccountService;
    private final SpecialityRepository specialityRepository;
    private final JwtService jwtService;
    private final RoleService roleService;
    private final PasswordEncoder encoder;


    //This method is in charge of create a RequestAccount instance and to wait be accepted by an Administrator
    //The Professional registered in this method have a 'WAITING' role which will changed to 'PROFESSIONAL 'when will be accepted
    //Will then be able to use your account normally
    @Override
    public RegisterResponse register(RegisterProfessionalRequest request) {


        //validate passwords matches
        String password = request.getPassword();
        String passwordRepeat = request.getConfirmPassword();
        if (!passwordRepeat.equals(password)) {
            throw new PasswordNotMatchesException();
        }

        //Encode password
        String passwordEncode = encoder.encode(password);

        //Assign role PENDIENT how initial role
        Role role = roleService.findRolePendient();

        //find speciality required to create the relationship in DB
        Speciality speciality = specialityRepository.findByName(request.getSpeciality());

        //create an object Professional and to save into DB
        Professional professional = getProfessional(request, passwordEncode, role, speciality);
        professionalRepository.save(professional);

        //create an object RequestAccount to wait be accepted
        RequestAccount requestAccount = new RequestAccount();
        requestAccount.setApplicant(professional);
        requestAccountService.saveRequestAccount(requestAccount);

        //generate authentication token JWT to be included into response
        String jwt = jwtService.generateToken(generateExtraClaims(professional,role),professional);

        //generate response object
        RegisterResponse response = new RegisterResponse();
        response.setUsername(professional.getUsername());
        response.setName(professional.getName());
        response.setLastName(professional.getLastname());
        response.setJwt(jwt);
        response.setIssueAt(new Date());
        response.setMessage("Professional Registered Successfully, must wait for approval an administrator for to use this account");
        return response;
    }


    //Get all professionals, accepted and pendient
    @Override
    public List<ShowProfessional> findAll(Pageable pageable) {

        Page<Professional> page = professionalRepository.findAll(pageable);
        return getShowProfessionals(page);
    }


    //Get all professional who are accepted
    @Override
    public List<ShowProfessional> findAcceptedProfessionals(Pageable pageable) {

        Page<Professional> page = professionalRepository.findAllByRoleName("PROFESSIONAL", pageable);
        List<ShowProfessional> response = getShowProfessionals(page);
        if(response.isEmpty()){
            throw new ResourceNotFoundException("Enabled Professionals");
        }
        return response;
    }

    //Get all professional who are pendient
    @Override
    public List<ShowProfessional> findPendientProfessionals(Pageable pageable) {
        Page<Professional> page = professionalRepository.findAllByRoleName("PENDIENT", pageable);
        List<ShowProfessional> response = getShowProfessionals(page);
        if(response.isEmpty()){
            throw new ResourceNotFoundException("Pendient Professionals");
        }
        return response;
    }

    @Override
    public void acceptAccount(Long id, Role roleProfessional) {
        professionalRepository.accept(id, roleProfessional);
    }

    @Override
    public Professional findProfessionalById(Long id) {
        Optional<Professional> professional = professionalRepository.findById(id);
        if(professional.isEmpty()){
            throw new ResourceNotFoundException("Professional Account");
        }
        return professional.get();
    }

    @Override
    public void deleteProfessionalById(Long id) {
        Professional professional = findProfessionalById(id);
        professionalRepository.deleteById(id);
    }


    //This function is used to generate a ShowProfessional List from a professionals page
    private static List<ShowProfessional> getShowProfessionals(Page<Professional> page) {
        List<ShowProfessional> list = new ArrayList<>();
        page.forEach(each -> {
            ShowProfessional professional = new ShowProfessional();
            professional.setProfessionalId(each.getId());
            professional.setName(each.getName());
            professional.setLastname(each.getLastname());
            professional.setSpeciality(each.getSpeciality().getName());
            if(each.getProfilePhoto()!=null){
                professional.setProfilePhoto(each.getProfilePhoto());
            }
            list.add(professional);
        });
        return list;
    }


    //generate a Professional object with the data required
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


    //generate the extra claims to be placed into jwt
    private static Map<String, Object> generateExtraClaims(Professional professional, Role role){

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", role.getName());
        extraClaims.put("authorities", professional.getAuthorities().stream().map((GrantedAuthority::getAuthority)).collect(Collectors.toList()));

        return extraClaims;
    }

}
