package com.medicalClinicProyect.MedicalClinic.service.impl;

import com.medicalClinicProyect.MedicalClinic.dto.RegisterAppointmentRequest;
import com.medicalClinicProyect.MedicalClinic.dto.ShowAppointment;
import com.medicalClinicProyect.MedicalClinic.entity.Appointment;
import com.medicalClinicProyect.MedicalClinic.entity.Patient;
import com.medicalClinicProyect.MedicalClinic.entity.Professional;
import com.medicalClinicProyect.MedicalClinic.exception.AppointmentNotAvailableException;
import com.medicalClinicProyect.MedicalClinic.exception.ResourceNotFoundException;
import com.medicalClinicProyect.MedicalClinic.exception.WrongAccountRequestException;
import com.medicalClinicProyect.MedicalClinic.repository.AppointmentRepository;
import com.medicalClinicProyect.MedicalClinic.security.CustomUserDetailsService;
import com.medicalClinicProyect.MedicalClinic.service.AppointmentService;
import com.medicalClinicProyect.MedicalClinic.service.PatientService;
import com.medicalClinicProyect.MedicalClinic.service.ProfessionalService;
import com.medicalClinicProyect.MedicalClinic.util.User;
import com.medicalClinicProyect.MedicalClinic.util.UtilityMethods;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ProfessionalService professionalService;
    private final PatientService patientService;
    private final CustomUserDetailsService userDetailsService;


    @Override
    public void addAppointment(RegisterAppointmentRequest request) {
        //get the user authenticated
        String professionalUsername = UtilityMethods.getAuthenticatedUsername();
        Professional professional = professionalService.findProfessionalByUsername(professionalUsername);

        //save the appointment with professional indicated
        generateAndSaveAppointment(request, professional);
    }


    //This function return all appointments exists
    @Override
    public List<ShowAppointment> findAll(Pageable pageable) {
        Page<Appointment> page = appointmentRepository.findAll(pageable);
        return getListShowAppointment(page);
    }


    //This function return a list of appointments considering if the user authenticated is a professional or a patient
    @Override
    public List<ShowAppointment> findAllByUser(Pageable pageable) {

        //get the user authenticated
        String username = UtilityMethods.getAuthenticatedUsername();
        User user = (User)userDetailsService.loadUserByUsername(username);

        //compare which instance the user is from and obtain your appointments
        if(user instanceof Professional){
            Page<Appointment> page = appointmentRepository.findAllByProfessionalId(((Professional) user).getId(), pageable);
            return getListShowAppointment(page);
        }
        if (user instanceof Patient) {
            Page<Appointment> page = appointmentRepository.findAllByPatientId(((Patient) user).getId(), pageable);
            return getListShowAppointment(page);
        }
        return null;
    }

    @Override
    public List<ShowAppointment> findAvailableAppointments(Pageable pageable) {
        Page<Appointment> page = appointmentRepository.findAllByStatus("AVAILABLE", pageable);
        return getListShowAppointment(page);
    }

    @Override
    public ShowAppointment bookAppointment(Long appointmentId) {

        String username = UtilityMethods.getAuthenticatedUsername();
        Patient patient = patientService.findPatientByUsername(username);
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);

        if(appointment.isEmpty() || !appointment.get().getStatus().equals("AVAILABLE")){
            throw new AppointmentNotAvailableException();
        }

        appointment.get().setPatient(patient);
        appointment.get().setStatus("UNAVAILABLE");
        appointmentRepository.save(appointment.get());
        return getShowAppointment(appointment.get());

    }

    //this function return a list of ShowAppointment objects from a previously obtained page
    private static List<ShowAppointment> getListShowAppointment(Page<Appointment> page) {
        List<ShowAppointment> response = new ArrayList<>();

        page.forEach(each -> {
            response.add(getShowAppointment(each));
        });
        return response;
    }

    private static ShowAppointment getShowAppointment(Appointment appointment) {

        //create a new ShowAppointment
        ShowAppointment response = new ShowAppointment();

        //if it has a patient associated, obtain their name and lastname and put in the field patient
        if(appointment.getPatient() != null){
            response.setPatientName(appointment.getPatient().getName()+" "+appointment.getPatient().getLastname());
        }
        response.setAppointmentId(appointment.getId());
        response.setProfessionalName(appointment.getProfessional().getLastname()+" "+appointment.getProfessional().getLastname());
        response.setProfessionalSpeciality(appointment.getProfessional().getSpeciality().getName());
        response.setDate(appointment.getAppointmentDate());
        response.setStatus(appointment.getStatus());
        return response;
    }

    private void generateAndSaveAppointment(RegisterAppointmentRequest request, Professional professional) {

        //create an appointment object
        Appointment appointment = new Appointment();

        //set your fields
        appointment.setAppointmentDate(LocalDateTime.parse(request.getDate()+" "+ request.getTime(), DateTimeFormatter.ofPattern("d/M/yyyy H:mm")));
        appointment.setProfessional(professional);
        //available as default
        appointment.setStatus("AVAILABLE");
        //save the appointment
        appointmentRepository.save(appointment);
    }
}
