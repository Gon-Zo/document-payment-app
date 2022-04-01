package com.example.documentapproval.service.dto;

import com.example.documentapproval.enums.RoleEnum;

/** User 정보 Projection */
public interface IUser {
  // id
  Long getId();

  // email
  String getEmail();

  // 비밀번호
  String getPassword();

  // 역할
  RoleEnum getRole();
}
