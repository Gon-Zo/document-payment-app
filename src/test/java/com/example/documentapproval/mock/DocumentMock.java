package com.example.documentapproval.mock;

import com.example.documentapproval.domain.Document;
import com.example.documentapproval.domain.PaymentComment;
import com.example.documentapproval.domain.User;
import com.example.documentapproval.enums.BoxType;
import com.example.documentapproval.enums.StateType;
import com.example.documentapproval.service.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.*;

public class DocumentMock {

  private static final Long DEFAULT_ID = 1L;

  private static final String DEFAULT_TITLE = "..";

  private static final String DEFAULT_CONTENT = "............";

  public static Document createMock(User user) {
    return Document.initBuilder()
        .id(DEFAULT_ID)
        .title(DEFAULT_TITLE)
        .content(DEFAULT_CONTENT)
        .division(DivisionMock.createMock())
        .user(user)
        .build();
  }

  public static Document createAllMock(User user) {

    Document mock =
        Document.initBuilder()
            .id(DEFAULT_ID)
            .title(DEFAULT_TITLE)
            .content(DEFAULT_CONTENT)
            .user(user)
            .build();

    List<PaymentComment> paymentCommentList =
        PaymentCommentMock.createPaymentCommentList(UserMock.createOtherUsers(), mock);

    mock.getPaymentCommentSet().addAll(paymentCommentList);

    return mock;
  }

  public static DocumentDTO createMockDTO() {
    return DocumentDTO.allBuilder()
        .title(DEFAULT_TITLE)
        .content(DEFAULT_CONTENT)
        .divisionCode("c001")
        .userIdList(List.of(2, 3))
        .build();
  }

  public static Page<DocumentInfo> createOutBoxList(User user, List<User> otherUsers) {

    PageRequest pageable = PageRequest.of(0, 10);

    Document mock = DocumentMock.createMock(user);

    List<PaymentComment> paymentCommentList =
        PaymentCommentMock.createPaymentCommentList(otherUsers, mock);

    mock.getPaymentCommentSet().addAll(paymentCommentList);

    DocumentInfo documentInfo = convertOf(mock);

    return new PageImpl<>(List.of(documentInfo), pageable, 1);
  }

  private static DocumentInfo convertOf(Document document) {
    return new DocumentInfo().setDocumentInfo(document);
  }

  private static PaymentCommentInfo convertOf(PaymentComment paymentComment) {
    return new PaymentCommentInfo().setPaymentCommentInfo(paymentComment);
  }

  public static DocumentBoxDTO createDocumentBoxDTO() {
    return DocumentBoxDTO.builder().page(0).size(10).type(BoxType.OUTBOX).build();
  }

  public static Optional<IDocument> createIDocument() {
    return Optional.of(
        new IDocument() {
          @Override
          public Long getId() {
            return 1L;
          }

          @Override
          public String getTitle() {
            return "test...";
          }

          @Override
          public String getContent() {
            return "teset.........";
          }

          @Override
          public String getDivisionName() {
            return "c001";
          }

          @Override
          public StateType getState() {
            return StateType.DEFAULT;
          }

          @Override
          public Integer getStep() {
            return 1;
          }

          @Override
          public Date getCreatedDate() {
            return new Date();
          }

          @Override
          public Date getUpdatedDate() {
            return new Date();
          }

          @Override
          public String getWriter() {
            return "tester";
          }

          @Override
          public Set<IPaymentComment> getPaymentCommentSet() {
            return new HashSet<>();
          }
        });
  }
}
