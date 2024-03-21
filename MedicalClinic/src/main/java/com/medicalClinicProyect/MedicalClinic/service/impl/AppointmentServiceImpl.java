package com.medicalClinicProyect.MedicalClinic.service.impl;

import com.medicalClinicProyect.MedicalClinic.dto.RegisterAppointmentRequest;
import com.medicalClinicProyect.MedicalClinic.dto.ShowAppointment;
import com.medicalClinicProyect.MedicalClinic.entity.Appointment;
import com.medicalClinicProyect.MedicalClinic.entity.Professional;
import com.medicalClinicProyect.MedicalClinic.repository.AppointmentRepository;
import com.medicalClinicProyect.MedicalClinic.service.AppointmentService;
import com.medicalClinicProyect.MedicalClinic.service.ProfessionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ProfessionalService professionalService;


    @Override
    public void addAppointment(RegisterAppointmentRequest request) {
        String professionalUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Professional professional = professionalService.findProfessionalByUsername(professionalUsername);

        generateAndSaveAppointment(request,professional);
    }

    @Override
    public List<ShowAppointment> findAll(Pageable pageable) {
        Page<Appointment> page = appointmentRepository.findAll(pageable);
        return getShowAppointment(page);
    }

    private static List<ShowAppointment> getShowAppointment(Page<Appointment> page) {
        List<ShowAppointment> response = new ArrayList<>();

        page.forEach(each -> {

            ShowAppointment appointment = new ShowAppointment();

            if(each.getPatient() != null){
                appointment.setPatientName(each.getPatient().getName()+" "+each.getPatient().getLastname());
            }
            appointment.setAppointmentId(each.getId());
            appointment.setProfessionalName(each.getProfessional().getLastname()+" "+each.getProfessional().getLastname());
            appointment.setProfessionalSpeciality(each.getProfessional().getSpeciality().getName());
            appointment.setDate(each.getAppointmentDate());
            response.add(appointment);
        });
        return response;
    }

    private void generateAndSaveAppointment(RegisterAppointmentRequest request, Professional professional) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(LocalDateTime.parse(request.getDate()+" "+ request.getTime(), DateTimeFormatter.ofPattern("d/M/yyyy H:mm")));
        appointment.setProfessional(professional);
        appointment.setStatus("FREE");
        appointmentRepository.save(appointment);
    }
}
