package io.example.documentapproval.utils;

import io.example.documentapproval.config.security.DomainUser;
import io.example.documentapproval.enums.ErrorCode;
import io.example.documentapproval.service.excpetion.AppException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityUtils {

    private SecurityUtils() {
    }

    public static String getLoginUserName() {
        return getLoginUser().getUsername();
    }

    public static Long getLoginUserId() {
        return getLoginUser().getId();
    }

    public static DomainUser getLoginUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return extractPrincipal(securityContext.getAuthentication())
                .orElseThrow(() -> new AppException(ErrorCode.EMPTY_NOT_USERNAME));
    }

    private static Optional<DomainUser> extractPrincipal(Authentication authentication) {
        if (ObjectUtils.isEmpty(authentication)) {
            throw new AppException(ErrorCode.EMPTY_NOT_USERNAME);
        } else if (authentication.getPrincipal() instanceof DomainUser) {
            return Optional.of((DomainUser) authentication.getPrincipal());
        } else {
            return Optional.empty();
        }
    }

}
