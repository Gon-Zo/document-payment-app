package io.example.documentapproval.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JapConfiguration {

    @Bean
    public DomainAuditorAware auditorAware() {
        return new DomainAuditorAware();
    }

}
