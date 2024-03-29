package com.medicalClinicProyect.MedicalClinic.service.impl;

import com.medicalClinicProyect.MedicalClinic.dto.requestDto.CancelAppointmentRequest;
import com.medicalClinicProyect.MedicalClinic.dto.requestDto.RegisterAppointmentRequest;
import com.medicalClinicProyect.MedicalClinic.dto.requestDto.ReportRequest;
import com.medicalClinicProyect.MedicalClinic.dto.showDto.ShowAppointment;
import com.medicalClinicProyect.MedicalClinic.entity.*;
import com.medicalClinicProyect.MedicalClinic.exception.AppointmentNotAvailableException;
import com.medicalClinicProyect.MedicalClinic.exception.CancelAppointmentException;
import com.medicalClinicProyect.MedicalClinic.repository.AppointmentRepository;
import com.medicalClinicProyect.MedicalClinic.service.*;
import com.medicalClinicProyect.MedicalClinic.util.User;
import com.medicalClinicProyect.MedicalClinic.util.UtilityMethods;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
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
    private final NotificationService notificationService;
    private final ReportService reportService;


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
            if(user instanceof Patient)return cancelAppointmentByPatient((Patient) user,appointmentId,cancelRequest);
            if(user instanceof Professional)return cancelAppointmentByProfessional((Professional) user,appointmentId,cancelRequest);
        }

        throw new CancelAppointmentException("cannot cancel appointment");
    }

    @Override
    public void generateReport(Long appointmentId, ReportRequest request) {

        //verify if appointment belongs user
        if(findAllByUser(null).stream().noneMatch(a -> Objects.equals(a.getAppointmentId(), appointmentId))){
            throw new CancelAppointmentException("Appointment don't belongs to user");
        }

        Appointment appointment = appointmentRepository.findById(appointmentId).get();
        Report report = new Report();

        //Generate report and save it
        report.setAppointment(appointment);
        report.setObservations(request.getObservations());
        reportService.generateReport(report);

        //change the appointment status to finished
        appointment.setStatus("FINISHED");
        appointmentRepository.save(appointment);

    }


    //this method is invoked when a patient cancel an appointment
    private ShowAppointment cancelAppointmentByPatient(Patient patient, Long appointmentId, CancelAppointmentRequest cancelRequest){

        //get the user who is cancel it
        String sender = patient.getUsername();
        //update the appointment status to canceled
        Appointment appointment = updateAppointmentToCanceled(sender, appointmentId, cancelRequest);

        //generate a notification to professional
        Professional professional = appointment.getProfessional();
        ProfessionalNotification notification = new ProfessionalNotification();
        notification.setProfessional(professional);
        notification.setMessage("Appointment: "+appointmentId+", date:"+appointment.getAppointmentDate().format(DateTimeFormatter.ofPattern("d/M/yyyy|H:m"))+
                " was canceled by "+patient.getUsername()+" you can view the reason in appointment messages");

        //send the notification to professional
        notificationService.sendNotificationToProfessional(notification);
        //update amount of appointments that was canceled by user
        updateCanceledAppointments(patient);
        //update the appointment in DB
        appointmentRepository.save(appointment);

        return getShowAppointment(appointment);

    }

    //this method is invoked when a professional cancel an appointment
    private ShowAppointment cancelAppointmentByProfessional(Professional professional, Long appointmentId, CancelAppointmentRequest cancelRequest){

        //get the user who is cancel it
        String sender = professional.getUsername();
        //update the appointment status to canceled
        Appointment appointment = updateAppointmentToCanceled(sender, appointmentId, cancelRequest);

        //generate a notification to patient
        Patient patient = appointment.getPatient();
        PatientNotification notification = new PatientNotification();
        notification.setPatient(patient);
        //need to put the reprogramming
        notification.setMessage("Appointment: "+appointmentId+", date:"+appointment.getAppointmentDate().format(DateTimeFormatter.ofPattern("d/M/yyyy|H:m"))+
                " was canceled by "+patient.getUsername()+" you can view the reason in appointment messages and accept or no the rescheduling");

        //send the notification to patient
        notificationService.sendNotificationToPatient(notification);
        //update amount of appointments that was canceled by user
        updateCanceledAppointments(patient);
        //update the appointment in DB
        appointmentRepository.save(appointment);

        return getShowAppointment(appointment);

    }

    //generate message and update appointment
    private Appointment updateAppointmentToCanceled(String sender, Long appointmentId, CancelAppointmentRequest cancelRequest) {
        Appointment appointment = appointmentRepository.findById(appointmentId).get();
        AppointmentMessage message = generateMessage(cancelRequest, sender);
        appointment.setStatus("CANCELED");
        appointment.setMessage(message);
        return appointment;
    }


    private boolean verifyToCancelAppointment(Long appointmentId){

        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        //verify if the appointment already was canceled
        if(appointment.isEmpty() || appointment.get().getStatus().equals("CANCELED")){
            return false;
        }

        //verify if appointment belongs user
        if(findAllByUser(null).stream().noneMatch(a -> Objects.equals(a.getAppointmentId(), appointmentId))){
            throw new CancelAppointmentException("Appointment don't belongs to user");
        }

        //verify if is before hoursToCancelAppointment
        if(ChronoUnit.HOURS.between(LocalDateTime.now(),appointment.get().getAppointmentDate()) < hoursToCancelAppointment){
           throw new CancelAppointmentException("Appointments cannot be canceled with less than " +hoursToCancelAppointment+ " hours notice");
        }

        return true;
    }

    //this method update amount of appointments canceled by a user and if it exceeds the allowed amount your account will be disabled
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
            if(userCanceledAppointments>cancelAppointmentsPermit)userDetailsService.disableUser(user);
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
