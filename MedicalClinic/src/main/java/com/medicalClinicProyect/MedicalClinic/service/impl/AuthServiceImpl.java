package com.medicalClinicProyect.MedicalClinic.service.impl;

import com.medicalClinicProyect.MedicalClinic.dto.requestDto.LoginRequest;
import com.medicalClinicProyect.MedicalClinic.dto.responseDto.LoginResponse;
import com.medicalClinicProyect.MedicalClinic.security.JwtService;
import com.medicalClinicProyect.MedicalClinic.service.AuthService;
import com.medicalClinicProyect.MedicalClinic.util.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;


    //This method realize the authentication
    public LoginResponse login(LoginRequest request){

        String username = request.getUsername();

        //create an Authentication object with username and password credential
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username, request.getPassword()
        );

        //invoke 'authenticate' method from authenticationManager for delegate the authentication to pertinent authenticationProvider
        authenticationManager.authenticate(authenticationToken);

        //bring the user from DB
        User user = (User) userDetailsService.loadUserByUsername(username);

        //generate token that after will be placed into response
        String jwt = jwtService.generateToken(generateExtraClaims(user),user);

        //create a response object
        LoginResponse response = new LoginResponse();
        response.setMessage("Successful Login!");
        response.setJwt(jwt);
        return response;
    }


    //generate extra claims for add to jwt
    private static Map<String,Object> generateExtraClaims(User user){

        Map<String,Object> extraClaims = new HashMap<>();
        extraClaims.put("role",user.getRole().getName());
        extraClaims.put("authorities", user.getAuthorities().stream().map((GrantedAuthority::getAuthority)).collect(Collectors.toList()));

        return extraClaims;
    }

}
