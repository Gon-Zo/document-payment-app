package com.example.documentapproval.mock;

import com.example.documentapproval.config.security.DomainUser;
import com.example.documentapproval.domain.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class SecurityMock {

  public static DomainUser createdDomainUser(User user) {

    List<SimpleGrantedAuthority> authorities =
        List.of(new SimpleGrantedAuthority(user.getRole().name()));

    return new DomainUser(user, authorities);
  }
}
