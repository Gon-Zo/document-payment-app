package com.example.documentapproval.mock;

import com.example.documentapproval.domain.Document;
import com.example.documentapproval.domain.PaymentComment;
import com.example.documentapproval.domain.PaymentCommentKey;
import com.example.documentapproval.domain.User;
import com.example.documentapproval.enums.StateType;
import com.example.documentapproval.service.dto.LiquidatedPaymentDTO;
import com.example.documentapproval.service.dto.PaymentCommentDTO;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PaymentCommentMock {

  public static List<PaymentComment> createUserPaymenutCommentList(
      User user, User otherUser, Document document) {
    return List.of(
        PaymentComment.allBuilder()
            .id(
                PaymentCommentKey.allBuilder()
                    .userId(otherUser.getId())
                    .documentId(document.getId())
                    .build())
            .step(1)
            .user(otherUser)
            .document(document)
            .state(StateType.OK)
            .build(),
        PaymentComment.allBuilder()
            .id(
                PaymentCommentKey.allBuilder()
                    .userId(user.getId())
                    .documentId(document.getId())
                    .build())
            .step(2)
            .user(user)
            .document(document)
            .state(StateType.DEFAULT)
            .build());
  }

  public static List<PaymentComment> createUserPaymenutCommentList(User user, Document document) {
    return List.of(
        PaymentComment.allBuilder()
            .id(
                PaymentCommentKey.allBuilder()
                    .userId(user.getId())
                    .documentId(document.getId())
                    .build())
            .step(1)
            .user(user)
            .document(document)
            .state(StateType.DEFAULT)
            .build());
  }

  public static List<PaymentComment> createPaymentCommentListPaymentFail(
      List<User> otherUsers, Document document) {

    return IntStream.range(0, otherUsers.size())
        .mapToObj(
            num -> {
              User user = otherUsers.get(num);
              return PaymentComment.allBuilder()
                  .id(
                      PaymentCommentKey.allBuilder()
                          .userId(user.getId())
                          .documentId(document.getId())
                          .build())
                  .step(num + 1)
                  .user(user)
                  .document(document)
                  .state(StateType.NO)
                  .build();
            })
        .collect(Collectors.toList());
  }

  public static List<PaymentComment> createPaymentCommentListPaymentSuccess(
      List<User> otherUsers, Document document) {

    return IntStream.range(0, otherUsers.size())
        .mapToObj(
            num -> {
              User user = otherUsers.get(num);
              return PaymentComment.allBuilder()
                  .id(
                      PaymentCommentKey.allBuilder()
                          .userId(user.getId())
                          .documentId(document.getId())
                          .build())
                  .step(num + 1)
                  .user(user)
                  .document(document)
                  .state(StateType.OK)
                  .build();
            })
        .collect(Collectors.toList());
  }

  public static List<PaymentComment> createPaymentCommentList(
      List<User> otherUsers, Document document) {

    return IntStream.range(0, otherUsers.size())
        .mapToObj(
            num -> {
              User user = otherUsers.get(num);
              return PaymentComment.allBuilder()
                  .id(
                      PaymentCommentKey.allBuilder()
                          .userId(user.getId())
                          .documentId(document.getId())
                          .build())
                  .step(num + 1)
                  .user(user)
                  .document(document)
                  .state(StateType.DEFAULT)
                  .build();
            })
        .collect(Collectors.toList());
  }

  public static List<PaymentComment> createPaymentCommentListToInit(
      List<User> otherUsers, Document document) {

    return IntStream.range(0, otherUsers.size())
        .mapToObj(
            num -> {
              User user = otherUsers.get(num);
              return PaymentComment.allBuilder()
                  .id(
                      PaymentCommentKey.allBuilder()
                          .userId(user.getId())
                          .documentId(document.getId())
                          .build())
                  .step(num + 1)
                  .state(StateType.DEFAULT)
                  .build();
            })
        .collect(Collectors.toList());
  }

  public static PaymentCommentDTO createSuccessPaymenuCommentDTO_OK() {
    return PaymentCommentDTO.resultBuilder()
        .entity(
            PaymentComment.allBuilder()
                .id(PaymentCommentKey.allBuilder().userId(1L).documentId(1L).build())
                .step(1)
                .comment("test...")
                .state(StateType.OK)
                .build())
        .build();
  }

  public static List<PaymentComment> createPaymentCommentList(Document document) {
    return List.of(
        PaymentComment.allBuilder()
            .id(PaymentCommentKey.allBuilder().userId(1L).documentId(1L).build())
            .user(document.getUser())
            .document(document)
            .step(1)
            .comment("test...")
            .state(StateType.DEFAULT)
            .build());
  }

  public static LiquidatedPaymentDTO createdDTO_OK() {
    return LiquidatedPaymentDTO.allBuilder()
        .state(StateType.OK)
        .comment("test.....")
        .documentId(1L)
        .build();
  }

  public static LiquidatedPaymentDTO createdDTO_web_OK() {
    return LiquidatedPaymentDTO.allBuilder().state(StateType.OK).comment("test.....").build();
  }
}
