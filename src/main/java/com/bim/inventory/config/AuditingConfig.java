//package com.bim.inventory.config;
//
//import org.springframework.data.domain.AuditorAware;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
//
//import java.util.Optional;
//
//@Configuration
//@EnableJpaAuditing
//public class AuditingConfig {
//    @Bean
//    public AuditorAware<String> auditorProvider() {
//        // Assuming you're using Spring Security, get the currently logged-in user
//        return () -> {
//            String username = SecurityContextHolder.getContext().getAuthentication().getName();
//            return Optional.ofNullable(username);
//        };
//    }
//}