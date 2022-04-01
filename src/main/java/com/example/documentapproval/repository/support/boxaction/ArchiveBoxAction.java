package com.example.documentapproval.repository.support.boxaction;

import com.example.documentapproval.enums.StateType;
import com.querydsl.core.BooleanBuilder;

import static com.example.documentapproval.domain.QDocument.document;
import static com.example.documentapproval.domain.QPaymentComment.paymentComment;

/** ArchiveBox List Up */
public class ArchiveBoxAction extends AbstractBoxAction implements IBoxAction {

  public ArchiveBoxAction(Long userId) {
    super(userId);
  }

  @Override
  public BooleanBuilder getBoxListInWhere() {

    BooleanBuilder booleanBuilder = new BooleanBuilder();

    Long userId = getUserId();

    booleanBuilder.and(document.user.id.eq(userId).or(paymentComment.id.userId.eq(userId)));

    booleanBuilder.and(document.state.in(StateType.OK, StateType.NO));

    return booleanBuilder;
  }
}
