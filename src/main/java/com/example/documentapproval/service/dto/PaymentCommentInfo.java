package com.example.documentapproval.service.dto;

import com.example.documentapproval.domain.PaymentComment;
import com.example.documentapproval.enums.StateType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PaymentCommentInfo {

  private Long userId;

  private String userEmail;

  private String comment;

  private Integer step;

  private StateType state;

  public PaymentCommentInfo setPaymentCommentInfo(PaymentComment paymentComment) {
    this.userId = paymentComment.getId().getUserId();
    this.userEmail = paymentComment.getUser().getEmail();
    this.comment = paymentComment.getComment();
    this.step = paymentComment.getStep();
    this.state = paymentComment.getState();
    return this;
  }
}
