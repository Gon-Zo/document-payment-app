package com.example.documentapproval.web.dosc;

import com.example.documentapproval.service.dto.LoginDTO;
import io.swagger.v3.oas.annotations.Operation;

public interface ViewResourceDocs {

  @Operation(
      summary = "로그인 API",
      description = "{\n" + "  \"email\": \"test1\",\n" + "  \"password\": \"admin\"\n" + "}")
  void login(LoginDTO dto);
}
