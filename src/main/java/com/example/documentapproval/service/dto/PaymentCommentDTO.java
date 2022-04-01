package com.example.documentapproval.service.dto;

import com.example.documentapproval.domain.PaymentComment;
import com.example.documentapproval.enums.StateType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentCommentDTO {

  private Long documentId;

  private Long userId;

  private StateType state;

  private Integer step;

  private String comment;

  @Builder(builderMethodName = "resultBuilder", builderClassName = "resultBuilder")
  private PaymentCommentDTO(PaymentComment entity) {
    this.documentId = entity.getId().getDocumentId();
    this.userId = entity.getId().getUserId();
    this.state = entity.getState();
    this.step = entity.getStep();
    this.comment = entity.getComment();
  }
}
