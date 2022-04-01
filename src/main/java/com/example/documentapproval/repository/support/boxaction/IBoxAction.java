package com.example.documentapproval.repository.support.boxaction;

import com.querydsl.core.BooleanBuilder;

/** Box Action 의 Where query 기능 IF */
public interface IBoxAction {

  /**
   * Querydsl 에서 Where 절을 리턴
   *
   * @return BooleanBuilder
   */
  BooleanBuilder getBoxListInWhere();
}
