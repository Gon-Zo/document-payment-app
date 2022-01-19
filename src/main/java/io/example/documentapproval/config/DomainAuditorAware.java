package io.example.documentapproval.config;

import io.example.documentapproval.utils.SecurityUtils;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class DomainAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SecurityUtils.getLoginUserName());
    }

}
