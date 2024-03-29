package com.medicalClinicProyect.MedicalClinic.service;

import com.medicalClinicProyect.MedicalClinic.dto.requestDto.LoginRequest;
import com.medicalClinicProyect.MedicalClinic.dto.responseDto.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}
