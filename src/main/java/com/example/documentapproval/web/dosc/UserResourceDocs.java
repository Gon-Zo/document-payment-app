package com.example.documentapproval.web.dosc;

import com.example.documentapproval.service.SignUpUserDTO;
import com.example.documentapproval.service.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;

public interface UserResourceDocs {

  @Operation(
      summary = "회원가입 API",
      description = "{\n" + "  \"email\": \"tester1234\",\n" + "  \"password\": \"123455\"\n" + "}")
  ResponseEntity<SignUpUserDTO> signupUser(UserDTO dto);
}
