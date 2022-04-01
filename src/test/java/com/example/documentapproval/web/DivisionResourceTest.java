package com.example.documentapproval.web;

import com.example.documentapproval.mock.DivisionMock;
import com.example.documentapproval.service.DivisionService;
import com.example.documentapproval.service.DocumentService;
import com.example.documentapproval.service.dto.IDivision;
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

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DivisionResource.class)
class DivisionResourceTest {

  private MockMvc mockMvc;

  @MockBean private com.example.documentapproval.config.InitConfiguration InitConfiguration;

  @MockBean private AuthenticationSuccessHandler authenticationSuccessHandler;

  @MockBean private AuthenticationFailureHandler authenticationFailureHandler;

  @MockBean private DocumentService documentService;

  @MockBean private DivisionService divisionService;

  @MockBean
  private com.example.documentapproval.service.PaymentCommentService PaymentCommentService;

  @BeforeEach
  void init() {
    mockMvc =
        MockMvcBuilders.standaloneSetup(new DivisionResource(divisionService))
            .addFilter(new CharacterEncodingFilter("UTF-8", true))
            .build();
  }

  @Test
  @DisplayName("마스터 분류 전체 조회 API")
  void showMasterDivisionList() throws Exception {

    List<IDivision> mockList = DivisionMock.createdDivisionList();

    BDDMockito.given(divisionService.getDivisionList()).willReturn(mockList);

    ResultActions action =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/api/divisions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8"))
            .andDo(print());

    BDDMockito.then(divisionService).should().getDivisionList();

    action
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].code").value(mockList.get(0).getCode()))
        .andExpect(jsonPath("$[0].name").value(mockList.get(0).getName()))
        .andExpect(jsonPath("$[1].code").value(mockList.get(1).getCode()))
        .andExpect(jsonPath("$[1].name").value(mockList.get(1).getName()))
        .andExpect(jsonPath("$[2].code").value(mockList.get(2).getCode()))
        .andExpect(jsonPath("$[2].name").value(mockList.get(2).getName()))
        .andExpect(jsonPath("$[3].code").value(mockList.get(3).getCode()))
        .andExpect(jsonPath("$[3].name").value(mockList.get(3).getName()))
        .andExpect(jsonPath("$[4].code").value(mockList.get(4).getCode()))
        .andExpect(jsonPath("$[4].name").value(mockList.get(4).getName()));
  }
}
