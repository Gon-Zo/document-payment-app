package com.example.documentapproval.service.dto;

import com.example.documentapproval.domain.User;
import com.example.documentapproval.enums.RoleEnum;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDTO {

  @NotEmpty private String email;

  @NotEmpty private String password;

  public User toEntity(String encodePwd) {
    return User.allBuilder().email(email).password(encodePwd).role(RoleEnum.ROLE_USER).build();
  }
}
