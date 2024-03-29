package com.medicalClinicProyect.MedicalClinic.service;

import com.medicalClinicProyect.MedicalClinic.dto.requestDto.AddPermissionRequest;
import com.medicalClinicProyect.MedicalClinic.dto.responseDto.ModifyPermissionResponse;

public interface GrantedAuthorityService {
    ModifyPermissionResponse deletePermission(Long permissionId);

    ModifyPermissionResponse addPermission(AddPermissionRequest newPermission);
}
