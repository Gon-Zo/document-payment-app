package com.example.documentapproval.web;

import com.example.documentapproval.config.security.DomainUser;
import com.example.documentapproval.domain.Document;
import com.example.documentapproval.domain.User;
import com.example.documentapproval.enums.BoxType;
import com.example.documentapproval.mock.*;
import com.example.documentapproval.repository.DocumentRepository;
import com.example.documentapproval.service.DocumentService;
import com.example.documentapproval.service.PaymentCommentService;
import com.example.documentapproval.service.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
@WebMvcTest(DocumentResource.class)
class DocumentResourceTest {

  private MockMvc mockMvc;

  private final String DEFAULT_API_URI = "/api/document";

  private final String DEFAULT_API_URI_2 = "/api/documents";

  @MockBean private com.example.documentapproval.config.InitConfiguration InitConfiguration;

  @MockBean private DocumentRepository documentRepository;

  @MockBean private AuthenticationSuccessHandler authenticationSuccessHandler;

  @MockBean private AuthenticationFailureHandler authenticationFailureHandler;

  @MockBean private DocumentService documentService;

  @MockBean private PaymentCommentService paymentCommentService;

  private User user = UserMock.createUser();

  @BeforeEach
  void init() {
    mockMvc =
        MockMvcBuilders.standaloneSetup(
                new DocumentResource(documentService, paymentCommentService))
            .addFilter(new CharacterEncodingFilter("UTF-8", true))
            .build();

    DomainUser domainUser = SecurityMock.createdDomainUser(user);

    SecurityContext context = SecurityContextHolder.getContext();

    context.setAuthentication(
        new UsernamePasswordAuthenticationToken(
            domainUser, domainUser.getPassword(), domainUser.getAuthorities()));
  }

  @Test
  @DisplayName("결제 문서 저장")
  void savedDocument() throws Exception {

    Document mock = DocumentMock.createMock(user);

    mock.getPaymentCommentSet()
        .addAll(PaymentCommentMock.createPaymentCommentList(UserMock.createOtherUsers(), mock));

    BDDMockito.given(documentService.createdDocument(any(), any())).willReturn(mock);

    DocumentDTO documentDTO = DocumentMock.createMockDTO();

    ResultActions action =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post(DEFAULT_API_URI)
                    .content(MockUtils.asJsonString(documentDTO))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8"))
            .andDo(print());

    BDDMockito.then(documentService).should().createdDocument(any(), any());

    action
        .andExpect(status().isOk())
        .andExpect(jsonPath("$['title']").value(mock.getTitle()))
        .andExpect(jsonPath("$['content']").value(mock.getContent()))
        .andExpect(jsonPath("$['division']['name']").value(mock.getDivision().getName()))
        .andExpect(jsonPath("$['user']['id']").value(mock.getUser().getId()))
        .andExpect(
            jsonPath("$['paymentCommentSet'].size()").value(mock.getPaymentCommentSet().size()));
  }

  @Test
  @DisplayName("결제함 API 테스트 케이스")
  void getDocuments() throws Exception {
    Page<DocumentInfo> mockList = DocumentMock.createOutBoxList(user, UserMock.createOtherUsers());

    BDDMockito.given(documentService.getByDocumentList(any(), any())).willReturn(mockList);

    ResultActions action =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get(DEFAULT_API_URI_2)
                    .param("type", BoxType.OUTBOX.name())
                    .param("page", String.valueOf(0))
                    .param("size", String.valueOf(10))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8"))
            .andDo(print());

    BDDMockito.then(documentService).should().getByDocumentList(any(), any());

    action
        .andExpect(status().isOk())
        .andExpect(jsonPath("$['content'][0]['id']").value(mockList.getContent().get(0).getId()))
        .andExpect(
            jsonPath("$['content'][0]['title']").value(mockList.getContent().get(0).getTitle()))
        .andExpect(
            jsonPath("$['content'][0]['content']").value(mockList.getContent().get(0).getContent()))
        .andExpect(
            jsonPath("$['content'][0]['writer']").value(mockList.getContent().get(0).getWriter()))
        .andExpect(
            jsonPath("$['content'][0]['divisionName']")
                .value(mockList.getContent().get(0).getDivisionName()));
  }

  @Test
  @DisplayName("결제 승인 API 테스트")
  void liquidatePaymentComment() throws Exception {

    PaymentCommentDTO mock = PaymentCommentMock.createSuccessPaymenuCommentDTO_OK();

    LiquidatedPaymentDTO dto = PaymentCommentMock.createdDTO_web_OK();

    BDDMockito.given(paymentCommentService.liquidateDocument(any(), any())).willReturn(mock);

    ResultActions action =
        mockMvc
            .perform(
                MockMvcRequestBuilders.put("/api/document/" + 1 + "/payment-comment")
                    .content(MockUtils.asJsonString(dto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8"))
            .andDo(print());

    BDDMockito.then(paymentCommentService).should().liquidateDocument(any(), any());

    action
        .andExpect(status().isOk())
        .andExpect(jsonPath("$['documentId']").value(mock.getDocumentId()))
        .andExpect(jsonPath("$['userId']").value(mock.getUserId()))
        .andExpect(jsonPath("$['state']").value(mock.getState().name()))
        .andExpect(jsonPath("$['step']").value(mock.getStep()));
  }

  @Test
  @DisplayName("결제 문서 상세 조회 API 테스트")
  void showDocumentOne() throws Exception {

    IDocument mock = DocumentMock.createIDocument().orElseThrow();

    BDDMockito.given(documentService.getDocumentOne(any())).willReturn(mock);

    ResultActions action =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/api/document/" + 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8"))
            .andDo(print());

    action
        .andExpect(status().isOk())
        .andExpect(jsonPath("$['id']").value(mock.getId()))
        .andExpect(jsonPath("$['title']").value(mock.getTitle()))
        .andExpect(jsonPath("$['content']").value(mock.getContent()))
        .andExpect(jsonPath("$['divisionName']").value(mock.getDivisionName()))
        .andExpect(jsonPath("$['state']").value(mock.getState().name()))
        .andExpect(jsonPath("$['step']").value(mock.getStep()))
        .andExpect(jsonPath("$['writer']").value(mock.getWriter()));
  }
}
