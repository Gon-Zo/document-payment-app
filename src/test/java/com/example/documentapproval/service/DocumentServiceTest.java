package com.example.documentapproval.service;

import com.example.documentapproval.domain.Division;
import com.example.documentapproval.domain.Document;
import com.example.documentapproval.domain.User;
import com.example.documentapproval.mock.DivisionMock;
import com.example.documentapproval.mock.DocumentMock;
import com.example.documentapproval.mock.PaymentCommentMock;
import com.example.documentapproval.mock.UserMock;
import com.example.documentapproval.repository.DocumentRepository;
import com.example.documentapproval.service.dto.DocumentBoxDTO;
import com.example.documentapproval.service.dto.DocumentInfo;
import com.example.documentapproval.service.dto.IDocument;
import com.example.documentapproval.service.dto.PaymentCommentInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DocumentServiceTest {

  private DocumentService documentService;

  @Mock private DocumentRepository documentRepository;

  private User user;

  private List<User> otherUsers;

  @BeforeEach
  void init() {
    MockitoAnnotations.openMocks(this);
    documentService = new DocumentServiceImpl(documentRepository);
    user = UserMock.createUser();
    otherUsers = UserMock.createOtherUsers();
  }

  @Test
  @DisplayName("결재 로직 저장 테스트 케이스")
  void createdDocument() {

    Document mock = DocumentMock.createMock(user);

    mock.getPaymentCommentSet()
        .addAll(PaymentCommentMock.createPaymentCommentList(UserMock.createOtherUsers(), mock));

    Optional<Division> divisionMock = DivisionMock.createMockToOptional();

    BDDMockito.given(documentRepository.save(any())).willReturn(mock);

    Document entity = documentService.createdDocument(DocumentMock.createMockDTO(), user);

    BDDMockito.then(documentRepository).should().save(any());

    Assertions.assertEquals(entity.getTitle(), mock.getTitle());
    Assertions.assertEquals(entity.getContent(), mock.getContent());
    Assertions.assertEquals(entity.getUser(), mock.getUser());
    Assertions.assertEquals(entity.getDivision(), divisionMock.get());
  }

  @Test
  @DisplayName("결제함 리스트 업 테스트 케이스")
  void getByDocumentList_outbox() {
    Page<DocumentInfo> mock = DocumentMock.createOutBoxList(user, otherUsers);

    BDDMockito.given(documentRepository.findByBoxAction(any(), any())).willReturn(mock);

    DocumentBoxDTO dto = DocumentMock.createDocumentBoxDTO();

    Page<DocumentInfo> entities = documentService.getByDocumentList(dto, user);

    BDDMockito.then(documentRepository).should().findByBoxAction(any(), any());

    org.assertj.core.api.Assertions.assertThat(entities).isEqualTo(mock);

    org.assertj.core.api.Assertions.assertThat(entities.getContent()).isEqualTo(mock.getContent());

    entities.forEach(
        entity -> {

          // id check
          DocumentInfo valueMock =
              mock.getContent().stream()
                  .filter(value -> value.getId().equals(entity.getId()))
                  .findFirst()
                  .orElseThrow();

          // 결제 타이틀
          Assertions.assertEquals(valueMock.getTitle(), entity.getTitle());

          // 결제 컨텐츠
          Assertions.assertEquals(valueMock.getContent(), entity.getContent());

          // 결제 작성자
          Assertions.assertEquals(valueMock.getWriter(), entity.getWriter());

          // 결제 상태
          Assertions.assertEquals(valueMock.getState(), entity.getState());

          // 결제 스텝
          Assertions.assertEquals(valueMock.getStep(), entity.getStep());
        });
  }

  @Test
  @DisplayName("결제 상세 문서 서비스 로직")
  void getDocumentOne() {

    Optional<IDocument> mockOptional = DocumentMock.createIDocument();

    BDDMockito.given(documentRepository.findById(any(), eq(IDocument.class)))
        .willReturn(mockOptional);

    IDocument entity = documentService.getDocumentOne(1L);

    BDDMockito.then(documentRepository).should().findById(any(), eq(IDocument.class));

    IDocument mock = mockOptional.get();

    Assertions.assertEquals(entity.getId(), mock.getId());
    Assertions.assertEquals(entity.getTitle(), mock.getTitle());
    Assertions.assertEquals(entity.getContent(), mock.getContent());
    Assertions.assertEquals(entity.getDivisionName(), mock.getDivisionName());
    Assertions.assertEquals(entity.getState(), mock.getState());
    Assertions.assertEquals(entity.getStep(), mock.getStep());
  }
}
