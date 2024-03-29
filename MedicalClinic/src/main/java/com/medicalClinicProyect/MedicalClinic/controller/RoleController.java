package com.medicalClinicProyect.MedicalClinic.controller;

import com.medicalClinicProyect.MedicalClinic.entity.Role;
import com.medicalClinicProyect.MedicalClinic.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> findAllRoles(){
        List<Role> roles = roleService.findAllRoles();
        return ResponseEntity.ok(roles);
    }

}
