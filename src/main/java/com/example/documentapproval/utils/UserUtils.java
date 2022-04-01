package com.example.documentapproval.utils;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

/** user utils */
public class UserUtils {

  /**
   * user role type to List<SimpleGrantedAuthority> 변경
   *
   * @param user - com.example.documentapproval.domain.User
   * @return List<SimpleGrantedAuthority>
   */
  public static List<SimpleGrantedAuthority> convertOf(
      com.example.documentapproval.domain.User user) {
    return List.of(new SimpleGrantedAuthority(user.getRole().name()));
  }

  public static List<SimpleGrantedAuthority> convertOf(String roleName) {
    return List.of(new SimpleGrantedAuthority(roleName));
  }
}
