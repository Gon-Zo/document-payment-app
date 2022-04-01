package com.example.documentapproval.repository.support.boxaction;

import com.example.documentapproval.enums.StateType;
import com.querydsl.core.BooleanBuilder;

import static com.example.documentapproval.domain.QDocument.document;

/** Out Box List Up Where Query Builder */
public class OutBoxAction extends AbstractBoxAction implements IBoxAction {

  public OutBoxAction(Long userId) {
    super(userId);
  }

  @Override
  public BooleanBuilder getBoxListInWhere() {

    BooleanBuilder booleanBuilder = new BooleanBuilder();

    Long userId = getUserId();

    booleanBuilder.and(document.user.id.eq(userId).and(document.state.eq(StateType.DEFAULT)));

    return booleanBuilder;
  }
}
