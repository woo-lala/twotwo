package com.sparta.twotwo.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@Configuration
public class JpaAuditingConfig {

    @Bean
    public AuditorAware<Long> auditorAware() {
        return new AuditorAwareImpl();
    }
}
