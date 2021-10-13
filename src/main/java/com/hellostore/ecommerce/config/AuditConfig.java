package com.hellostore.ecommerce.config;

import com.hellostore.ecommerce.util.SecurityUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class AuditConfig {

    @Bean
    @Profile("default")
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of(SecurityUtil.getCurrentUsername());
    }
}
