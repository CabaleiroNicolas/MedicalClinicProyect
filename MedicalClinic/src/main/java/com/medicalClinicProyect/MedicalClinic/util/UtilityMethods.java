package com.medicalClinicProyect.MedicalClinic.util;


import org.springframework.security.core.context.SecurityContextHolder;

public class UtilityMethods {

    public static String getAuthenticatedUsername(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if(SecurityContextHolder.getContext().getAuthentication() == null || username == null){
            username = "User not authenticated: Anonymous";
        }
        return username;
    }
}
