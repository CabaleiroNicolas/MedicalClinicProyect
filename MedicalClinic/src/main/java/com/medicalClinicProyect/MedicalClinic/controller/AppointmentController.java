package com.medicalClinicProyect.MedicalClinic.controller;

import com.medicalClinicProyect.MedicalClinic.dto.RegisterAppointmentRequest;
import com.medicalClinicProyect.MedicalClinic.dto.ShowAdministrator;
import com.medicalClinicProyect.MedicalClinic.dto.ShowAppointment;
import com.medicalClinicProyect.MedicalClinic.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;


    @GetMapping
    public ResponseEntity<List<ShowAppointment>> findAllAppointments(@PageableDefault(size = 5) Pageable pageable){

        List<ShowAppointment> response = appointmentService.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addNewAppointment(@RequestBody RegisterAppointmentRequest request){

        appointmentService.addAppointment(request);
        return ResponseEntity.ok("Appointment created successfully");
    }

}
