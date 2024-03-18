package com.medicalClinicProyect.MedicalClinic.service.impl;

import com.medicalClinicProyect.MedicalClinic.dto.AddPermissionRequest;
import com.medicalClinicProyect.MedicalClinic.dto.ModifyPermissionResponse;
import com.medicalClinicProyect.MedicalClinic.entity.Authority;
import com.medicalClinicProyect.MedicalClinic.entity.GrantedAuthority;
import com.medicalClinicProyect.MedicalClinic.entity.Role;
import com.medicalClinicProyect.MedicalClinic.exception.ResourceNotFoundException;
import com.medicalClinicProyect.MedicalClinic.repository.AuthorityRepository;
import com.medicalClinicProyect.MedicalClinic.repository.GrantedAuthorityRepository;
import com.medicalClinicProyect.MedicalClinic.repository.RoleRepository;
import com.medicalClinicProyect.MedicalClinic.service.GrantedAuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GrantedAuthorityServiceImpl implements GrantedAuthorityService {

    private final GrantedAuthorityRepository grantedAuthorityRepository;
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    public ModifyPermissionResponse deletePermission(Long permissionId) {

        //look for a GrantedPermission with permissionId, if it exists, is deleted,otherwise throw an exception
        Optional<GrantedAuthority> permission = grantedAuthorityRepository.findById(permissionId);
        if(permission.isEmpty()){
            throw new ResourceNotFoundException("Permission with id: "+permissionId);
        }
        grantedAuthorityRepository.deleteById(permissionId);

        //Generate the response object
        ModifyPermissionResponse response = new ModifyPermissionResponse();
        response.setMessage("Permission was successfully deleted");
        response.setAuthorityModified(permission.get().getAuthority().getName());
        response.setRole(permission.get().getRole().getName());

        return response;

    }

    @Override
    public ModifyPermissionResponse addPermission(AddPermissionRequest newPermission) {

        //look for the two parameters, if on of them don´t exists throw an exception
        Optional<Role> role = roleRepository.findByName(newPermission.getRoleName());
        Optional<Authority> authority = authorityRepository.findById(newPermission.getAuthorityId());
        if(role.isEmpty() || authority.isEmpty()){
            throw new ResourceNotFoundException("Role or authority");
        }

        //If they exist, create a new GrantedPermission with them
        GrantedAuthority permission = new GrantedAuthority();
        permission.setRole(role.get());
        permission.setAuthority(authority.get());
        grantedAuthorityRepository.save(permission);

        //Create a response object
        ModifyPermissionResponse response = new ModifyPermissionResponse();
        response.setMessage("Permission added Successfully");
        response.setAuthorityModified(authority.get().getName());
        response.setRole(role.get().getName());
        return response;
    }
}
