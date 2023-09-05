package com.bim.inventory.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return Optional.of(loggedInUsername);
    }

}