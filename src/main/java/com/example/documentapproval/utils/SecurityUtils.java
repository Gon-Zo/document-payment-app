package com.example.documentapproval.utils;

import com.example.documentapproval.config.exception.NoDataException;
import com.example.documentapproval.config.security.DomainUser;
import com.example.documentapproval.domain.User;
import com.example.documentapproval.enums.MessageType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public final class SecurityUtils {

  public static User getLoginUser() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    return extractPrincipal(securityContext.getAuthentication())
        .orElseThrow(() -> new NoDataException(MessageType.NoSecurityUtilsData))
        .getUser();
  }

  private static Optional<DomainUser> extractPrincipal(Authentication authentication) {
    if (authentication == null) {
      throw new NoDataException(MessageType.NoSecurityUtilsData);
    } else if (authentication.getPrincipal() instanceof DomainUser) {
      return Optional.of((DomainUser) authentication.getPrincipal());
    } else {
      return Optional.empty();
    }
  }
}
