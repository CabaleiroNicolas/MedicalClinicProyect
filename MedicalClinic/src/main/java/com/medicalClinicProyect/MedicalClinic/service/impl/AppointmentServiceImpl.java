package com.medicalClinicProyect.MedicalClinic.service.impl;

import com.medicalClinicProyect.MedicalClinic.dto.RegisterAppointmentRequest;
import com.medicalClinicProyect.MedicalClinic.dto.ShowAppointment;
import com.medicalClinicProyect.MedicalClinic.entity.Appointment;
import com.medicalClinicProyect.MedicalClinic.entity.Patient;
import com.medicalClinicProyect.MedicalClinic.entity.Professional;
import com.medicalClinicProyect.MedicalClinic.exception.ResourceNotFoundException;
import com.medicalClinicProyect.MedicalClinic.exception.WrongAccountRequestException;
import com.medicalClinicProyect.MedicalClinic.repository.AppointmentRepository;
import com.medicalClinicProyect.MedicalClinic.security.CustomUserDetailsService;
import com.medicalClinicProyect.MedicalClinic.service.AppointmentService;
import com.medicalClinicProyect.MedicalClinic.service.ProfessionalService;
import com.medicalClinicProyect.MedicalClinic.util.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    private final CustomUserDetailsService userDetailsService;


    @Override
    public void addAppointment(RegisterAppointmentRequest request) {
        //get the user authenticated
        String professionalUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Professional professional = professionalService.findProfessionalByUsername(professionalUsername);

        //save the appointment with professional indicated
        generateAndSaveAppointment(request, professional);
    }


    //This function return a list of appointments considering if the user authenticated is a professional or a patient
    @Override
    public List<ShowAppointment> findAllByUser(Pageable pageable) {

        //get the user authenticated
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = (User)userDetailsService.loadUserByUsername(username);

        //compare which instance the user is from and obtain your appointments
        if(user instanceof Professional){
            Page<Appointment> page = appointmentRepository.findAllByProfessionalId(((Professional) user).getId(), pageable);
            return getShowAppointment(page);
        }
        if (user instanceof Patient) {
            Page<Appointment> page = appointmentRepository.findAllByPatientId(((Patient) user).getId(), pageable);
            return getShowAppointment(page);
        }
        return null;
    }

    //This function return all appointments exists
    @Override
    public List<ShowAppointment> findAll(Pageable pageable) {
        Page<Appointment> page = appointmentRepository.findAll(pageable);
        return getShowAppointment(page);
    }

    //this function return a list of ShowAppointment objects from a previously obtained page
    private static List<ShowAppointment> getShowAppointment(Page<Appointment> page) {
        List<ShowAppointment> response = new ArrayList<>();

        page.forEach(each -> {

            //create a new ShowAppointment object for each page element
            ShowAppointment appointment = new ShowAppointment();

            //if it has a patient associated, obtain their name and lastname and put in the field patient
            if(each.getPatient() != null){
                appointment.setPatientName(each.getPatient().getName()+" "+each.getPatient().getLastname());
            }
            //save the object in list
            appointment.setAppointmentId(each.getId());
            appointment.setProfessionalName(each.getProfessional().getLastname()+" "+each.getProfessional().getLastname());
            appointment.setProfessionalSpeciality(each.getProfessional().getSpeciality().getName());
            appointment.setDate(each.getAppointmentDate());
            response.add(appointment);
        });
        return response;
    }

    private void generateAndSaveAppointment(RegisterAppointmentRequest request, Professional professional) {

        //create an appointment object
        Appointment appointment = new Appointment();

        //set your fields
        appointment.setAppointmentDate(LocalDateTime.parse(request.getDate()+" "+ request.getTime(), DateTimeFormatter.ofPattern("d/M/yyyy H:mm")));
        appointment.setProfessional(professional);
        //free as default
        appointment.setStatus("FREE");
        //save the appointment
        appointmentRepository.save(appointment);
    }
}
