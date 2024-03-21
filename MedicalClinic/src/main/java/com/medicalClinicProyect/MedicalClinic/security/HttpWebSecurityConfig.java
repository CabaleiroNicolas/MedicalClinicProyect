package com.medicalClinicProyect.MedicalClinic.security;

import com.medicalClinicProyect.MedicalClinic.exception.CustomAccessDeniedHandler;
import com.medicalClinicProyect.MedicalClinic.exception.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class HttpWebSecurityConfig{


    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthFilter jwtAuthFilter;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);
        http.authenticationProvider(authenticationProvider);
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling(handling->{
            handling.authenticationEntryPoint(authenticationEntryPoint);
            handling.accessDeniedHandler(accessDeniedHandler);
        });
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers(
                    "auth/login",
                            "patient/register",
                            "professional/register")
            .permitAll();
            auth.requestMatchers("admin/**").hasRole("ADMINISTRATOR");
            auth.requestMatchers("professional").hasAuthority("SHOW_PROFESSIONALS_ACCEPTED");
            auth.requestMatchers("professional/all").hasAuthority("LIST_PROFESSIONALS");
            auth.requestMatchers("professional/pendient").hasAuthority("SHOW_PROFESSIONALS_PENDIENT");
            auth.requestMatchers("patient").hasAuthority("LIST_PATIENTS");
            auth.requestMatchers("admin").hasAuthority("LIST_ADMINISTRATORS");
            auth.requestMatchers("appointment/all").hasAuthority("LIST_APPOINTMENTS");
            auth.requestMatchers("appointment/add").hasAuthority("CREATE_APPOINTMENT");
            auth.requestMatchers("appointment").hasAuthority("SHOW_MY_APPOINTMENTS");
            auth.requestMatchers("permission/**").hasAuthority("MODIFY_AUTHORITIES");
            auth.requestMatchers("role").hasAuthority("LIST_ROLES");
            auth.requestMatchers("authority").hasAuthority("LIST_AUTHORITIES");
            auth.requestMatchers("profile/update/**").hasAuthority("UPDATE_MY_PROFILE");

            auth.anyRequest().authenticated();
        });

        return http.build();
    }
}
