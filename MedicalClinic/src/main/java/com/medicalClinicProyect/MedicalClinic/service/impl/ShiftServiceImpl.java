package com.medicalClinicProyect.MedicalClinic.service.impl;

import com.medicalClinicProyect.MedicalClinic.dto.ShowProfessional;
import com.medicalClinicProyect.MedicalClinic.dto.ShowShift;
import com.medicalClinicProyect.MedicalClinic.entity.Professional;
import com.medicalClinicProyect.MedicalClinic.entity.Shift;
import com.medicalClinicProyect.MedicalClinic.repository.ShiftRepository;
import com.medicalClinicProyect.MedicalClinic.service.ShiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShiftServiceImpl implements ShiftService {

    private final ShiftRepository shiftRepositoy;

    @Override
    public List<ShowShift> findAll(Pageable pageable) {
        Page<Shift> page = shiftRepositoy.findAll(pageable);
        return getShowShift(page);
    }

    private static List<ShowShift> getShowShift(Page<Shift> page) {
        List<ShowShift> response = new ArrayList<>();

        page.forEach(each -> {

            String professionalName = each.getProfessional().getName();
            String professionalLastname = each.getProfessional().getLastname();
            String patientName = each.getPatient().getName();
            String patientLastname = each.getPatient().getLastname();

            ShowShift shift = new ShowShift();
            shift.setShiftId(each.getId());
            shift.setProfessionalName(professionalName+" "+professionalLastname);
            shift.setProfessionalSpeciality(each.getProfessional().getSpeciality().getName());
            shift.setPatientName(patientName+" "+patientLastname);
            response.add(shift);
        });
        return response;
    }
}
