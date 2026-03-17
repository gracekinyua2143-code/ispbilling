package com.leavemanagementsystem.leave.config;

import com.leavemanagementsystem.leave.security.JwtFilter;

import com.leavemanagementsystem.leave.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> {})   // ENABLE CORS
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        // PUBLIC
                        .requestMatchers("/api/auth/**").permitAll()

                        // SUPER ADMIN
                        .requestMatchers("/api/superadmin/**")
                        .permitAll()

                        // ADMIN
                        .requestMatchers("/api/admin/**")
                        .hasAnyAuthority("ADMIN","SUPER_ADMIN")

                        // DM
                        .requestMatchers("/api/dm/**")
                        .hasAuthority("DM")

                        // HR
                        .requestMatchers("/api/hr/**")
                        .hasAnyAuthority("HR","SUPER_ADMIN")

                        // MANAGERS
                        .requestMatchers("/api/manager/**")
                        .hasAnyAuthority("TM","CM","SUPER_ADMIN")

                        // AUDITOR
                        .requestMatchers("/api/audit/**")
                        .hasAnyAuthority("INTERNAL_AUDITOR","SUPER_ADMIN")

                        // EMPLOYEES
                        .requestMatchers("/api/employee/**")
                        .hasAnyAuthority("EMPLOYEE","SUPER_ADMIN","ADMIN")
                        // EMPLOYEES
                      //  .requestMatchers("/api/employee/**")
                      //  .permitAll()


                        .anyRequest().authenticated()
                )

                .userDetailsService(userDetailsService)

                .addFilterBefore(jwtFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer(){
            @Override
            public void addCorsMappings(CorsRegistry registry){
                registry.addMapping("/**")
                        .allowedOrigins(
                                "http://localhost:5173",
                                "http://192.168.15.26:4567"
                        )
                        .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

}