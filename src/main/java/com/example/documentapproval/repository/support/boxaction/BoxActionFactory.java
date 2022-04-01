package com.example.documentapproval.repository.support.boxaction;

import com.example.documentapproval.config.exception.NoDataException;
import com.example.documentapproval.enums.BoxType;
import com.example.documentapproval.enums.MessageType;
import com.querydsl.core.BooleanBuilder;

import java.util.Optional;

/** Box Action 에서 Type 별로 각각 다른 객체를 리턴 해주는 객체 */
public class BoxActionFactory {

  private IBoxAction boxAction;

  public BoxActionFactory() {}

  private BoxActionFactory(IBoxAction iBoxAction) {
    this.boxAction = iBoxAction;
  }

  public BooleanBuilder getBoxListInWhere() {
    return boxAction.getBoxListInWhere();
  }

  /**
   * 상황별 action box 를 주입 해준다.
   *
   * @param boxType - box 별 타입 (OUTBOX, INBOX, ARCHIVE, NULL)
   * @param userId - 유저의 아이디
   * @return BoxActionFactory
   */
  public BoxActionFactory getBoxAction(BoxType boxType, Long userId) {

    BoxType switchType = Optional.ofNullable(boxType).orElse(BoxType.NULL);

    switch (switchType) {
      case OUTBOX:
        boxAction = new OutBoxAction(userId);
        break;
      case ARCHIVE:
        boxAction = new ArchiveBoxAction(userId);
        break;
      case INBOX:
        boxAction = new InBoxAction(userId);
        break;
      default:
        throw new NoDataException(MessageType.NoBoxTypeData);
    }

    return this;
  }
}
