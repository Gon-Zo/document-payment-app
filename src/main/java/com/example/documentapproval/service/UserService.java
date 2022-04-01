package com.example.documentapproval.service;

import com.example.documentapproval.service.dto.UserDTO;

// 유저 서비스 IF
public interface UserService {

  /**
   * 회원 가입 로직
   *
   * @param dto - UserDTO 유저 정보 DTO
   * @return User
   */
  SignUpUserDTO signupUser(UserDTO dto);
}
