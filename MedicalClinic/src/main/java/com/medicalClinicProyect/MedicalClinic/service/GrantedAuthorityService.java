package com.medicalClinicProyect.MedicalClinic.service;

import com.medicalClinicProyect.MedicalClinic.dto.AddPermissionRequest;
import com.medicalClinicProyect.MedicalClinic.dto.ModifyPermissionResponse;

public interface GrantedAuthorityService {
    ModifyPermissionResponse deletePermission(Long permissionId);

    ModifyPermissionResponse addPermission(AddPermissionRequest newPermission);
}
