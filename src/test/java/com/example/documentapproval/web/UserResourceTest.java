package com.example.documentapproval.web;

import com.example.documentapproval.mock.MockUtils;
import com.example.documentapproval.mock.UserMock;
import com.example.documentapproval.service.DocumentService;
import com.example.documentapproval.service.PaymentCommentService;
import com.example.documentapproval.service.SignUpUserDTO;
import com.example.documentapproval.service.UserService;
import com.example.documentapproval.service.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserResource.class)
class UserResourceTest {

  private MockMvc mockMvc;

  @MockBean private UserService userService;

  @MockBean private AuthenticationSuccessHandler authenticationSuccessHandler;

  @MockBean private AuthenticationFailureHandler authenticationFailureHandler;

  @MockBean private DocumentService documentService;

  @MockBean private PaymentCommentService paymentCommentService;

  @MockBean private com.example.documentapproval.config.InitConfiguration InitConfiguration;

  @BeforeEach
  void init() {
    mockMvc =
        MockMvcBuilders.standaloneSetup(new UserResource(userService))
            .addFilter(new CharacterEncodingFilter("UTF-8", true))
            .build();
  }

  @Test
  @DisplayName("회원 가입 로직 테스트 케이스")
  void signInUser() throws Exception {

    UserDTO dto = UserMock.createUserDTO();

    SignUpUserDTO mock = UserMock.createUserNotAdmin_singin();

    BDDMockito.given(userService.signupUser(any())).willReturn(mock);

    ResultActions action =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/user")
                    .content(MockUtils.asJsonString(dto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8"))
            .andDo(print());

    action
        .andExpect(status().isOk())
        .andExpect(jsonPath("$['id']").value(mock.getId()))
        .andExpect(jsonPath("$['email']").value(mock.getEmail()))
        .andExpect(jsonPath("$['role']").value(mock.getRole().name()));
  }
}
