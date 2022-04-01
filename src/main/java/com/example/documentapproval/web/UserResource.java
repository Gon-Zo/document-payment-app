package com.example.documentapproval.web;

import com.example.documentapproval.service.SignUpUserDTO;
import com.example.documentapproval.service.UserService;
import com.example.documentapproval.service.dto.UserDTO;
import com.example.documentapproval.web.dosc.UserResourceDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserResource implements UserResourceDocs {

  private final UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<SignUpUserDTO> signupUser(@RequestBody @Valid UserDTO dto) {
    SignUpUserDTO body = userService.signupUser(dto);
    return ResponseEntity.ok(body);
  }
}
