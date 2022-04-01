package com.example.documentapproval.config.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

/**
 * 시큐리티에서 사용하는 DomainUser 클래스 <br>
 * jpa User entity 객체를 담기 위해서 사용되는 클래스 이다.
 */
public class DomainUser extends User {

  private final com.example.documentapproval.domain.User user;

  public DomainUser(
      com.example.documentapproval.domain.User user, List<SimpleGrantedAuthority> authorities) {
    super(user.getEmail(), user.getPassword(), authorities);
    this.user = user;
  }

  public com.example.documentapproval.domain.User getUser() {
    return user;
  }
}
