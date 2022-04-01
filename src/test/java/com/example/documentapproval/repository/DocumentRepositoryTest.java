package com.example.documentapproval.repository;

import com.example.documentapproval.config.InitConfiguration;
import com.example.documentapproval.config.QueryDslConfiguration;
import com.example.documentapproval.domain.Division;
import com.example.documentapproval.domain.Document;
import com.example.documentapproval.domain.PaymentComment;
import com.example.documentapproval.domain.User;
import com.example.documentapproval.enums.BoxType;
import com.example.documentapproval.enums.StateType;
import com.example.documentapproval.mock.DivisionMock;
import com.example.documentapproval.mock.DocumentMock;
import com.example.documentapproval.mock.PaymentCommentMock;
import com.example.documentapproval.mock.UserMock;
import com.example.documentapproval.repository.support.boxaction.BoxActionFactory;
import com.example.documentapproval.service.dto.DocumentInfo;
import com.example.documentapproval.service.dto.IDocument;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@Import(value = {QueryDslConfiguration.class, InitConfiguration.class})
class DocumentRepositoryTest {

  @Autowired private DocumentRepository documentRepository;

  @Autowired private UserRepository userRepository;

  @Autowired private DivisionRepository divisionRepository;

  private User user;

  private List<Division> divisions;

  private List<User> otherUsers;

  @BeforeEach
  void init() {

    divisions = divisionRepository.saveAll(DivisionMock.createMasterDivision());

    divisionRepository.flush();

    user = userRepository.save(UserMock.createUser());
    otherUsers = userRepository.saveAll(UserMock.createOtherUsers());

    userRepository.flush();
  }

  @Test
  @DisplayName("저장 로직 테스트 케이스")
  void save() {

    Document mock = DocumentMock.createMock(user);

    Document entity = documentRepository.save(mock);

    List<PaymentComment> paymentCommentList =
        PaymentCommentMock.createPaymentCommentListToInit(otherUsers, entity);

    entity.getPaymentCommentSet().addAll(paymentCommentList);

    mock.getPaymentCommentSet().addAll(paymentCommentList);

    org.assertj.core.api.Assertions.assertThat(entity).isEqualTo(mock);

    Assertions.assertEquals(mock.getId(), entity.getId());
    Assertions.assertEquals(mock.getTitle(), entity.getTitle());
    Assertions.assertEquals(mock.getContent(), entity.getContent());
    Assertions.assertEquals(mock.getUser(), entity.getUser());
    Assertions.assertEquals(mock.getPaymentCommentSet(), entity.getPaymentCommentSet());
    Assertions.assertEquals(entity.getStep(), 1);
    Assertions.assertEquals(entity.getState(), StateType.DEFAULT);
  }

  @Nested
  @DisplayName("조회")
  class Select {

    private Document mock;

    private List<Document> mockList;

    @BeforeEach
    void init() {
      mock = documentRepository.save(DocumentMock.createMock(user));

      List<PaymentComment> paymentCommentList =
          PaymentCommentMock.createPaymentCommentList(otherUsers, mock);

      mock.getPaymentCommentSet().addAll(paymentCommentList);

      Document mock2 =
          documentRepository.save(
              Document.initBuilder()
                  .id(2L)
                  .title("test2 success")
                  .content("test success")
                  .user(user)
                  .division(divisions.get(0))
                  .build());

      Document mock3 =
          documentRepository.save(
              Document.initBuilder()
                  .id(3L)
                  .title("test3 fail")
                  .content("test3 fail")
                  .user(user)
                  .division(divisions.get(0))
                  .build());

      mock2
          .getPaymentCommentSet()
          .addAll(PaymentCommentMock.createPaymentCommentListPaymentSuccess(otherUsers, mock2));

      mock2.approveState();

      mock3
          .getPaymentCommentSet()
          .addAll(PaymentCommentMock.createPaymentCommentListPaymentFail(otherUsers, mock3));

      mock3.refuseState();

      Document mock4 =
          documentRepository.save(
              Document.initBuilder()
                  .id(4L)
                  .title("결제 해야 될 문서")
                  .content("결제 해야 될 문서")
                  .user(otherUsers.get(0))
                  .division(divisions.get(0))
                  .build());

      mock4
          .getPaymentCommentSet()
          .addAll(PaymentCommentMock.createUserPaymenutCommentList(user, mock4));

      Document mock5 =
          documentRepository.save(
              Document.initBuilder()
                  .id(5L)
                  .title("결제 해야 될 문서 2")
                  .content("결제 해야 될 문서 2")
                  .user(otherUsers.get(0))
                  .division(divisions.get(0))
                  .build());

      mock5.proceedState();

      mock5
          .getPaymentCommentSet()
          .addAll(PaymentCommentMock.createUserPaymenutCommentList(user, otherUsers.get(1), mock5));

      mockList = List.of(mock, mock2, mock3, mock4, mock5);
    }

    @Test
    @DisplayName("아이디 별로 조회 프로젝션 테스트 케이스")
    void findById_projection() {

      Optional<IDocument> entityOptional =
          documentRepository.findById(mock.getId(), IDocument.class);

      Assertions.assertTrue(entityOptional.isPresent());

      IDocument entity = entityOptional.get();

      Assertions.assertEquals(mock.getId(), entity.getId());
      Assertions.assertEquals(mock.getTitle(), entity.getTitle());
      Assertions.assertEquals(mock.getContent(), entity.getContent());
      Assertions.assertEquals(mock.getDivision().getName(), entity.getDivisionName());
      Assertions.assertEquals(mock.getState(), entity.getState());
      Assertions.assertEquals(mock.getStep(), entity.getStep());
      Assertions.assertEquals(mock.getUser().getEmail(), entity.getWriter());

      entity
          .getPaymentCommentSet()
          .forEach(
              value -> {
                PaymentComment mockPaymenut =
                    mock.getPaymentCommentSet().stream()
                        .filter(mockVal -> mockVal.getId().getUserId().equals(value.getUserId()))
                        .findFirst()
                        .orElseThrow();

                Assertions.assertEquals(mockPaymenut.getState(), value.getState());
                Assertions.assertEquals(mockPaymenut.getComment(), value.getComment());
                Assertions.assertEquals(mockPaymenut.getStep(), value.getStep());
              });
    }

    @Test
    @DisplayName("유저의 아이디 별로 프로젝션 테스트 케이스")
    void findByUser_Id_projection() {
      List<IDocument> entities = documentRepository.findByUser_Id(user.getId(), IDocument.class);

      IDocument entity =
          entities.stream()
              .filter(value -> value.getId().equals(mock.getId()))
              .findFirst()
              .orElseThrow();

      Assertions.assertEquals(mock.getId(), entity.getId());
      Assertions.assertEquals(mock.getTitle(), entity.getTitle());
      Assertions.assertEquals(mock.getContent(), entity.getContent());
      Assertions.assertEquals(mock.getDivision().getName(), entity.getDivisionName());
      Assertions.assertEquals(mock.getState(), entity.getState());
      Assertions.assertEquals(mock.getStep(), entity.getStep());
      Assertions.assertEquals(mock.getUser().getEmail(), entity.getWriter());

      entity
          .getPaymentCommentSet()
          .forEach(
              value -> {
                PaymentComment mockPaymenut =
                    mock.getPaymentCommentSet().stream()
                        .filter(mockVal -> mockVal.getId().getUserId().equals(value.getUserId()))
                        .findFirst()
                        .orElseThrow();

                Assertions.assertEquals(mockPaymenut.getState(), value.getState());
                Assertions.assertEquals(mockPaymenut.getComment(), value.getComment());
                Assertions.assertEquals(mockPaymenut.getStep(), value.getStep());
              });
    }

    @Test
    @DisplayName("outbox 테스트 케이스")
    void findBy_OutBox() {

      PageRequest pageable = PageRequest.of(0, 10);

      BoxActionFactory factory = new BoxActionFactory().getBoxAction(BoxType.OUTBOX, user.getId());

      Page<DocumentInfo> list = documentRepository.findByBoxAction(pageable, factory);

      List<DocumentInfo> content = list.getContent();

      // 데이터 더미에 outbox 조건으로 하나만 던져준다,
      Assertions.assertEquals(content.size(), 1);

      DocumentInfo entity = content.get(0);

      Document mock =
          mockList.stream()
              .filter(checkedMock -> checkedMock.getId().equals(entity.getId()))
              .findFirst()
              .orElseThrow();

      // 테이블 PK
      Assertions.assertEquals(mock.getId(), entity.getId());
      // 제목
      Assertions.assertEquals(mock.getTitle(), entity.getTitle());
      // 컨텐츠
      Assertions.assertEquals(mock.getContent(), entity.getContent());
      // 글쓴이명
      Assertions.assertEquals(mock.getUser().getEmail(), entity.getWriter());
      // 분류명
      Assertions.assertEquals(mock.getDivision().getName(), entity.getDivisionName());
      // 현재 상태
      Assertions.assertEquals(mock.getState(), entity.getState());
      // 현재 스텝
      Assertions.assertEquals(mock.getStep(), entity.getStep());
    }

    @Test
    @DisplayName("Archive 테스트 케이스")
    void findBy_Archive() {

      PageRequest pageable = PageRequest.of(0, 10);

      BoxActionFactory factory = new BoxActionFactory().getBoxAction(BoxType.ARCHIVE, user.getId());

      Page<DocumentInfo> list = documentRepository.findByBoxAction(pageable, factory);

      List<DocumentInfo> content = list.getContent();

      // 데이터 더미에 Archive 조건으로 하나만 던져준다,
      Assertions.assertEquals(content.size(), 2);

      content.forEach(
          entity -> {
            Document mock =
                mockList.stream()
                    .filter(value -> entity.getId().equals(value.getId()))
                    .findFirst()
                    .orElseThrow();

            // 테이블 PK
            Assertions.assertEquals(mock.getId(), entity.getId());
            // 제목
            Assertions.assertEquals(mock.getTitle(), entity.getTitle());
            // 컨텐츠
            Assertions.assertEquals(mock.getContent(), entity.getContent());
            // 글쓴이명
            Assertions.assertEquals(mock.getUser().getEmail(), entity.getWriter());
            // 분류명
            Assertions.assertEquals(mock.getDivision().getName(), entity.getDivisionName());
            // 현재 상태
            Assertions.assertEquals(mock.getState(), entity.getState());
            // 현재 스텝
            Assertions.assertEquals(mock.getStep(), entity.getStep());
          });
    }

    @Test
    @DisplayName("inbox 테스트 케이스")
    void findBy_InBox() {
      PageRequest pageable = PageRequest.of(0, 10);

      BoxActionFactory factory = new BoxActionFactory().getBoxAction(BoxType.INBOX, user.getId());

      Page<DocumentInfo> list = documentRepository.findByBoxAction(pageable, factory);

      List<DocumentInfo> content = list.getContent();

      // 데이터 더미에 inbox 조건으로 하나만 던져준다,
      Assertions.assertEquals(content.size(), 2);

      content.forEach(
          entity -> {
            Document mock =
                mockList.stream()
                    .filter(value -> entity.getId().equals(value.getId()))
                    .findFirst()
                    .orElseThrow();

            // 테이블 PK
            Assertions.assertEquals(mock.getId(), entity.getId());
            // 제목
            Assertions.assertEquals(mock.getTitle(), entity.getTitle());
            // 컨텐츠
            Assertions.assertEquals(mock.getContent(), entity.getContent());
            // 글쓴이명
            Assertions.assertEquals(mock.getUser().getEmail(), entity.getWriter());
            // 분류명
            Assertions.assertEquals(mock.getDivision().getName(), entity.getDivisionName());
            // 현재 상태
            Assertions.assertEquals(mock.getState(), entity.getState());
            // 현재 스텝
            Assertions.assertEquals(mock.getStep(), entity.getStep());
          });
    }
  }
}
