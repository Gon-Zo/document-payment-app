package com.example.documentapproval.repository.support.boxaction;

/** box action 추상화 클래스 */
public abstract class AbstractBoxAction {

  private final Long userId;

  protected AbstractBoxAction(Long userId) {
    this.userId = userId;
  }

  protected Long getUserId() {
    return userId;
  }
}
