package com.example.documentapproval.repository.support.boxaction;

import com.example.documentapproval.enums.StateType;
import com.querydsl.core.BooleanBuilder;

import static com.example.documentapproval.domain.QDocument.document;
import static com.example.documentapproval.domain.QPaymentComment.paymentComment;

public class InBoxAction extends AbstractBoxAction implements IBoxAction {

  public InBoxAction(Long userId) {
    super(userId);
  }

  @Override
  public BooleanBuilder getBoxListInWhere() {

    BooleanBuilder booleanBuilder = new BooleanBuilder();

    Long userId = getUserId();

    booleanBuilder.and(
        paymentComment.id.userId.eq(userId).and(paymentComment.step.eq(document.step)));

    booleanBuilder.and(document.state.eq(StateType.DEFAULT));

    return booleanBuilder;
  }
}
