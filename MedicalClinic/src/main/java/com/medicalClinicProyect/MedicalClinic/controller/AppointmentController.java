package com.medicalClinicProyect.MedicalClinic.controller;

import com.medicalClinicProyect.MedicalClinic.dto.CancelAppointmentRequest;
import com.medicalClinicProyect.MedicalClinic.dto.RegisterAppointmentRequest;
import com.medicalClinicProyect.MedicalClinic.dto.ReportRequest;
import com.medicalClinicProyect.MedicalClinic.dto.ShowAppointment;
import com.medicalClinicProyect.MedicalClinic.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;


    //get the appointments associated with a user
    @GetMapping
    public ResponseEntity<List<ShowAppointment>> findUserAppointments(@PageableDefault(size = 5) Pageable pageable){

        List<ShowAppointment> response = appointmentService.findAllByUser(pageable);
        return ResponseEntity.ok(response);
    }

    //get all appointments
    @GetMapping("/all")
    public ResponseEntity<List<ShowAppointment>> findAllAppointments(@PageableDefault(size = 5) Pageable pageable){

        List<ShowAppointment> response = appointmentService.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/available")
    public ResponseEntity<List<ShowAppointment>> findAvailableAppointments(@PageableDefault(size = 5) Pageable pageable){

        List<ShowAppointment> response = appointmentService.findAvailableAppointments(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<ShowAppointment>> findPendingAppointments(@PageableDefault(size = 5) Pageable pageable,
                                                                         @RequestParam(required = false) String day){

        List<ShowAppointment> response = appointmentService.findAllPending(pageable, day);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addNewAppointment(@RequestBody RegisterAppointmentRequest request){

        appointmentService.addAppointment(request);
        return ResponseEntity.ok("Appointment created successfully");
    }

    @PutMapping("/reserve/{appointmentId}")
    public ResponseEntity<ShowAppointment> bookAppointment(@PathVariable Long appointmentId){

        ShowAppointment response = appointmentService.bookAppointment(appointmentId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/cancel/{appointmentId}")
    public ResponseEntity<ShowAppointment> cancelAppointment(@PathVariable Long appointmentId,
                                                             @RequestBody CancelAppointmentRequest cancelRequest){

        ShowAppointment response = appointmentService.cancelAppointment(appointmentId, cancelRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/report/{appointmentId}")
    public ResponseEntity<String> generateReport(@PathVariable Long appointmentId, @RequestBody ReportRequest request){

        appointmentService.generateReport(appointmentId, request);
        return ResponseEntity.ok("Report generated successfully!");
    }


}
