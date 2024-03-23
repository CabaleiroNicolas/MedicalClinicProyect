package com.medicalClinicProyect.MedicalClinic.service.impl;

import com.medicalClinicProyect.MedicalClinic.dto.CancelAppointmentRequest;
import com.medicalClinicProyect.MedicalClinic.dto.RegisterAppointmentRequest;
import com.medicalClinicProyect.MedicalClinic.dto.ShowAppointment;
import com.medicalClinicProyect.MedicalClinic.entity.Appointment;
import com.medicalClinicProyect.MedicalClinic.entity.AppointmentMessage;
import com.medicalClinicProyect.MedicalClinic.entity.Patient;
import com.medicalClinicProyect.MedicalClinic.entity.Professional;
import com.medicalClinicProyect.MedicalClinic.exception.AppointmentNotAvailableException;
import com.medicalClinicProyect.MedicalClinic.exception.CancelAppointmentException;
import com.medicalClinicProyect.MedicalClinic.repository.AppointmentRepository;
import com.medicalClinicProyect.MedicalClinic.security.CustomUserDetailsService;
import com.medicalClinicProyect.MedicalClinic.service.AppointmentService;
import com.medicalClinicProyect.MedicalClinic.service.PatientService;
import com.medicalClinicProyect.MedicalClinic.service.ProfessionalService;
import com.medicalClinicProyect.MedicalClinic.util.User;
import com.medicalClinicProyect.MedicalClinic.util.UtilityMethods;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    @Value("${cancel.appointments.permit}")
    private Long cancelAppointmentsPermit;
    @Value("${hours.to.cancel.appointment}")
    private Long hoursToCancelAppointment;

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

        //get the user authenticated
        String username = UtilityMethods.getAuthenticatedUsername();
        Patient patient = patientService.findPatientByUsername(username);
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);

        //if the appointment is not available or doesn't exist throw an exception
        if(appointment.isEmpty() || !appointment.get().getStatus().equals("AVAILABLE")){
            throw new AppointmentNotAvailableException();
        }

        //if appointment is available, add the patient and switch status to 'UNAVAILABLE'
        appointment.get().setPatient(patient);
        appointment.get().setStatus("UNAVAILABLE");
        appointmentRepository.save(appointment.get());
        return getShowAppointment(appointment.get());

    }

    @Override
    public List<ShowAppointment> findAllPending(Pageable pageable, String day) {

        //filter the user appointments by are unavailable
        List<ShowAppointment> list = findAllByUser(pageable).stream()
                .filter(each -> each.getStatus().equals("UNAVAILABLE")).toList();

        //if exist the day parameter, filter the unavailable appointment list by the date specified
            if(day != null){
                LocalDate date = LocalDate.parse(day,DateTimeFormatter.ofPattern("d/M/yyyy"));
                return list.stream().
                        filter(appointmentDate ->
                        appointmentDate.getDate().toLocalDate().equals(date)).toList();
            }
        return list;
    }

    @Override
    public ShowAppointment cancelAppointment(Long appointmentId, CancelAppointmentRequest cancelRequest) {

        //get the user authenticated
        String username = UtilityMethods.getAuthenticatedUsername();
        User user = (User) userDetailsService.loadUserByUsername(username);


        //Verify if the appointment belongs user and is before hoursToCancelAppointment
        if(verifyToCancelAppointment(appointmentId)){

            //generate message and update appointment
            Appointment appointment = appointmentRepository.findById(appointmentId).get();
            AppointmentMessage message = generateMessage(cancelRequest, username);
            appointment.setStatus("CANCELED");
            appointment.setMessage(message);

            updateCanceledAppointments(user);

            appointmentRepository.save(appointment);

            return getShowAppointment(appointment);
        }

        throw new CancelAppointmentException("cannot cancel appointment");
    }



    private boolean verifyToCancelAppointment(Long appointmentId){

        PageRequest pageable = PageRequest.of(0,0);

        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        if(appointment.isEmpty()){
            return false;
        }

        //verify if appointment belongs user
        if(findAllByUser(pageable).stream().noneMatch(a -> Objects.equals(a.getAppointmentId(), appointmentId))){
            throw new CancelAppointmentException("Appointment don't belongs to user");
        }

        //verify if is before hoursToCancelAppointment
       if(ChronoUnit.HOURS.between(appointment.get().getAppointmentDate(), LocalDateTime.now()) < hoursToCancelAppointment){
           throw new CancelAppointmentException("Appointments cannot be canceled with less than " +hoursToCancelAppointment+ " hours notice");
       }

        return true;
    }

    private void updateCanceledAppointments(User user) {
        long userCanceledAppointments;

        if(user instanceof Patient){
            userCanceledAppointments = ((Patient) user).getCanceledAppointments()+1;
            ((Patient) user).setCanceledAppointments(userCanceledAppointments);
            if(userCanceledAppointments>cancelAppointmentsPermit)userDetailsService.disableUser(user);
            patientService.save((Patient) user);
        }
        if(user instanceof Professional){
            userCanceledAppointments = ((Professional) user).getCanceledAppointments()+1;
            ((Professional) user).setCanceledAppointments(userCanceledAppointments);
            if(userCanceledAppointments>3)userDetailsService.disableUser(user);
            professionalService.save((Professional) user);
        }
    }

    private static AppointmentMessage generateMessage(CancelAppointmentRequest cancelRequest, String username) {
        AppointmentMessage message = new AppointmentMessage();
        message.setCause(cancelRequest.getCause());
        message.setSender(username);
        message.setIssueAt(LocalDateTime.now());
        return message;
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
