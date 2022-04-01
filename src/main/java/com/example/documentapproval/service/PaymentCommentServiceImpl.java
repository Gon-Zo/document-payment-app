package com.example.documentapproval.service;

import com.example.documentapproval.config.exception.NoDataException;
import com.example.documentapproval.domain.Document;
import com.example.documentapproval.domain.PaymentComment;
import com.example.documentapproval.domain.User;
import com.example.documentapproval.enums.MessageType;
import com.example.documentapproval.enums.StateType;
import com.example.documentapproval.repository.PaymentCommentRepository;
import com.example.documentapproval.service.dto.LiquidatedPaymentDTO;
import com.example.documentapproval.service.dto.PaymentCommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.documentapproval.enums.StateType.NO;
import static com.example.documentapproval.enums.StateType.OK;

@Service
@RequiredArgsConstructor
public class PaymentCommentServiceImpl implements PaymentCommentService {

  private final PaymentCommentRepository paymentCommentRepository;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public PaymentCommentDTO liquidateDocument(LiquidatedPaymentDTO dto, User user) {

    // 선택된 결제 문서의 승인자 리스트업
    List<PaymentComment> paymentCommentList =
        paymentCommentRepository.findById_DocumentId(dto.getDocumentId());

    // 현재 로그인한 유저의 결제 데이터를 가지고 온다.
    PaymentComment userPaymentComment =
        paymentCommentList.stream()
            .filter(paymentComment -> user.getId().equals(paymentComment.getId().getUserId()))
            .findFirst()
            .orElseThrow(() -> new NoDataException(MessageType.NoPaymentCommentUser));

    StateType state = dto.getState();

    // 결재 승인 데이터의 상태와 이유를 업데이트
    userPaymentComment.updateCommentAndState(dto.getComment(), state);

    // 현재 결제 문서의 데이터를 가지고 온다.
    Document document = userPaymentComment.getDocument();

    if (NO.equals(state)) {
      // 결재 문서의 NO 가 되면 바로 결재문서의 상태를 NO 로 생성
      document.refuseState();
      return PaymentCommentDTO.resultBuilder().entity(userPaymentComment).build();
    }

    // 모든 사용자의 승인 여부
    boolean isOk =
        paymentCommentList.stream()
            .allMatch(paymentComment -> OK.equals(paymentComment.getState()));

    if (isOk) {
      // 현재 결재 문서의 상태를 OK 로 변경
      document.approveState();
    } else {
      // 현재 결재 스텝을 +1 시킨다.
      document.proceedState();
    }

    return PaymentCommentDTO.resultBuilder().entity(userPaymentComment).build();
  }
}
