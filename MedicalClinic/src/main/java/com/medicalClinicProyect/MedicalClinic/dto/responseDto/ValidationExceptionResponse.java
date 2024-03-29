package com.medicalClinicProyect.MedicalClinic.dto.responseDto;

import com.medicalClinicProyect.MedicalClinic.dto.responseDto.ExceptionResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ValidationExceptionResponse extends ExceptionResponse {

    private Map<String, String> errors;
}
