package com.medicalClinicProyect.MedicalClinic.service;

import com.medicalClinicProyect.MedicalClinic.dto.ShowShift;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShiftService {
    List<ShowShift> findAll(Pageable pageable);
}
