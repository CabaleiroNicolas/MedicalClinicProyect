package com.medicalClinicProyect.MedicalClinic.controller;

import com.medicalClinicProyect.MedicalClinic.dto.AddPermissionRequest;
import com.medicalClinicProyect.MedicalClinic.dto.ModifyPermissionResponse;
import com.medicalClinicProyect.MedicalClinic.service.GrantedAuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/permission")
public class PermissionController {

    private final GrantedAuthorityService authorityService;

    @DeleteMapping("/delete/{permissionId}")
    public ResponseEntity<ModifyPermissionResponse> deletePermission(@PathVariable Long permissionId){

        ModifyPermissionResponse response = authorityService.deletePermission(permissionId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<ModifyPermissionResponse> addPermission(@RequestBody AddPermissionRequest newPermission){

        ModifyPermissionResponse response = authorityService.addPermission(newPermission);
        return ResponseEntity.ok(response);
    }
}
