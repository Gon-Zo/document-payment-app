package com.example.documentapproval.repository;

import com.example.documentapproval.config.InitConfiguration;
import com.example.documentapproval.config.QueryDslConfiguration;
import com.example.documentapproval.domain.Division;
import com.example.documentapproval.domain.Document;
import com.example.documentapproval.domain.PaymentComment;
import com.example.documentapproval.domain.User;
import com.example.documentapproval.mock.DivisionMock;
import com.example.documentapproval.mock.DocumentMock;
import com.example.documentapproval.mock.PaymentCommentMock;
import com.example.documentapproval.mock.UserMock;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@Import(value = {QueryDslConfiguration.class, InitConfiguration.class})
class PaymentCommentRepositoryTest {

  @Autowired private UserRepository userRepository;

  @Autowired private DocumentRepository documentRepository;

  @Autowired private PaymentCommentRepository paymentCommentRepository;

  @Autowired private DivisionRepository divisionRepository;

  private User user;

  private List<User> otherUsers;

  private Document document;

  private List<Division> divisions;

  @BeforeEach
  void init() {

    divisions = divisionRepository.saveAll(DivisionMock.createMasterDivision());

    divisionRepository.flush();

    user = userRepository.save(UserMock.createUser());

    otherUsers = userRepository.saveAll(UserMock.createOtherUsers());

    document = documentRepository.save(DocumentMock.createMock(user));
  }

  @Test
  @DisplayName("승인 유저들 저장 로직 테스트 케이스")
  void saveAll() {

    List<PaymentComment> mock = PaymentCommentMock.createPaymentCommentList(otherUsers, document);

    List<PaymentComment> entities = paymentCommentRepository.saveAll(mock);

    paymentCommentRepository.flush();

    org.assertj.core.api.Assertions.assertThat(entities).isEqualTo(mock);
    Assertions.assertEquals(entities.size(), mock.size());
    Assertions.assertFalse(entities.isEmpty());
    Assertions.assertEquals(entities.get(0).getId(), mock.get(0).getId());
    Assertions.assertEquals(entities.get(0).getState(), mock.get(0).getState());
    Assertions.assertEquals(entities.get(0).getComment(), mock.get(0).getComment());
  }

  @Nested
  @DisplayName("조회")
  class Select {

    private List<PaymentComment> mock;

    @BeforeEach
    void init() {

      mock =
          paymentCommentRepository.saveAll(
              PaymentCommentMock.createPaymentCommentList(otherUsers, document));

      paymentCommentRepository.flush();
    }

    @Test
    @DisplayName("결제 아이디 찾는 로직 테스트 케이스")
    void findById_DocumentId() {

      List<PaymentComment> entities =
          paymentCommentRepository.findById_DocumentId(document.getId());

      org.assertj.core.api.Assertions.assertThat(entities).isEqualTo(mock);
      Assertions.assertEquals(entities.get(0).getId(), mock.get(0).getId());
      Assertions.assertEquals(entities.get(0).getState(), mock.get(0).getState());
      Assertions.assertEquals(entities.get(0).getComment(), mock.get(0).getComment());
      Assertions.assertNotNull(entities.get(0).getDocument());
      Assertions.assertEquals(entities.get(0).getDocument(), mock.get(0).getDocument());
    }
  }
}
