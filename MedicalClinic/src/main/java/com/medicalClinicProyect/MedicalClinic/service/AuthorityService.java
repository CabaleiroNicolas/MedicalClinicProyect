package com.medicalClinicProyect.MedicalClinic.service;

import com.medicalClinicProyect.MedicalClinic.entity.Authority;
import com.medicalClinicProyect.MedicalClinic.repository.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AuthorityService {


    Authority findAuthorityById(Long authorityId);

    List<Authority> findAllAuthorities();
}
