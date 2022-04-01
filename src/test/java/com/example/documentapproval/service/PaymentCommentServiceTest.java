package com.example.documentapproval.service;

import com.example.documentapproval.domain.Document;
import com.example.documentapproval.domain.PaymentComment;
import com.example.documentapproval.domain.User;
import com.example.documentapproval.mock.DocumentMock;
import com.example.documentapproval.mock.PaymentCommentMock;
import com.example.documentapproval.mock.UserMock;
import com.example.documentapproval.repository.PaymentCommentRepository;
import com.example.documentapproval.service.dto.LiquidatedPaymentDTO;
import com.example.documentapproval.service.dto.PaymentCommentDTO;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PaymentCommentServiceTest {

  @Mock private PaymentCommentRepository paymentCommentRepository;

  private PaymentCommentService paymentCommentService;

  private User user;

  @BeforeEach
  void init() {
    MockitoAnnotations.openMocks(this);
    paymentCommentService = new PaymentCommentServiceImpl(paymentCommentRepository);
    user = UserMock.createUser();
  }

  @Test
  @DisplayName("결제 승인 여부 테스트 케이스")
  void liquidateDocument() {

    Document document = DocumentMock.createMock(user);

    List<PaymentComment> mockList = PaymentCommentMock.createPaymentCommentList(document);

    BDDMockito.given(paymentCommentRepository.findById_DocumentId(any())).willReturn(mockList);

    LiquidatedPaymentDTO dto = PaymentCommentMock.createdDTO_OK();

    PaymentCommentDTO resultDTO = paymentCommentService.liquidateDocument(dto, user);

    BDDMockito.then(paymentCommentRepository).should().findById_DocumentId(any());

    Assertions.assertEquals(resultDTO.getDocumentId(), dto.getDocumentId());
    Assertions.assertEquals(resultDTO.getComment(), dto.getComment());
    Assertions.assertEquals(resultDTO.getState(), dto.getState());
    Assertions.assertEquals(resultDTO.getUserId(), user.getId());
  }
}
