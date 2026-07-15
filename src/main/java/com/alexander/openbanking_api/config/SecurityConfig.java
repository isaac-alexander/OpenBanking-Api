//package com.alexander.openbanking_api.config;
//
//import com.alexander.openbanking_api.security.CustomUserDetailsService;
//import com.alexander.openbanking_api.security.JwtAuthenticationFilter;
//import lombok.RequiredArgsConstructor;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//
//import org.springframework.security.config.http.SessionCreationPolicy;
//
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import org.springframework.security.web.SecurityFilterChain;
//
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    private final CustomUserDetailsService userDetailsService;
//
//    private final PasswordEncoder passwordEncoder;
//
//    // configure security rules
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http)
//            throws Exception {
//
//        http
//
//                .csrf(csrf -> csrf.disable())
//
//                .sessionManagement(session ->
//                        session.sessionCreationPolicy(
//                                SessionCreationPolicy.STATELESS))
//
//                .authorizeHttpRequests(auth -> auth
//
//                        // authentication endpoints
//                        .requestMatchers("/api/auth/**").permitAll()
//
//                        // swagger
//                        .requestMatchers(
//                                "/swagger-ui/**",
//                                "/v3/api-docs/**")
//                        .permitAll()
//
//                        // everything else requires authentication
//                        .anyRequest()
//                        .authenticated())
//
//                .authenticationProvider(authenticationProvider())
//
//                .addFilterBefore(jwtAuthenticationFilter,
//                        UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//
//    }
//
//    // password authentication provider
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//
//        return new DaoAuthenticationProvider(
//                userDetailsService,
//                passwordEncoder
//        );
//
//    }
//
//    // authentication manager
//    @Bean
//    public AuthenticationManager authenticationManager(
//            AuthenticationConfiguration configuration)
//            throws Exception {
//
//        return configuration.getAuthenticationManager();
//
//    }
//
//}