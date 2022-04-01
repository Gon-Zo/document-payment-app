package com.example.documentapproval.mock;

import com.example.documentapproval.domain.User;
import com.example.documentapproval.enums.RoleEnum;
import com.example.documentapproval.service.SignUpUserDTO;
import com.example.documentapproval.service.dto.UserDTO;

import java.util.List;

public class UserMock {

  public static User createUser() {
    return User.allBuilder()
        .id(1L)
        .email("test@naver.com")
        .password("123456")
        .role(RoleEnum.ROLE_ADMIN)
        .build();
  }

  public static User createOtherUser() {
    return User.allBuilder()
        .id(2L)
        .email("test1@naver.com")
        .password("12345")
        .role(RoleEnum.ROLE_ADMIN)
        .build();
  }

  public static User createUserEmptyRole() {
    return User.allBuilder().id(1L).email("test@naver.com").password("123456").build();
  }

  public static List<User> createUsers() {
    return List.of(createUser(), createOtherUser());
  }

  public static List<User> createAllUsers() {
    return List.of(createUser(), createOtherUser());
  }

  public static List<User> createOtherUsers() {
    return List.of(
        createOtherUser(),
        User.allBuilder()
            .id(3L)
            .email("test2@naver.com")
            .password("12345")
            .role(RoleEnum.ROLE_ADMIN)
            .build());
  }

  public static User createUserNotAdmin() {
    return User.allBuilder().id(1L).email("test@naver.com").role(RoleEnum.ROLE_USER).build();
  }

  public static SignUpUserDTO createUserNotAdmin_singin() {
    User entity = createUserNotAdmin();
    return SignUpUserDTO.builder()
        .id(entity.getId())
        .email(entity.getEmail())
        .role(entity.getRole())
        .createdDate(entity.getCreatedDate())
        .updatedDate(entity.getUpdatedDate())
        .build();
  }

  public static UserDTO createUserDTO() {
    return UserDTO.builder().email("test@naver.com").password("123456").build();
  }
}
