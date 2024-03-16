package com.medicalClinicProyect.MedicalClinic.service;

import com.medicalClinicProyect.MedicalClinic.dto.LoginRequest;
import com.medicalClinicProyect.MedicalClinic.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}
