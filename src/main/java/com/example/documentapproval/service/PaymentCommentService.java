package com.example.documentapproval.service;

import com.example.documentapproval.domain.PaymentComment;
import com.example.documentapproval.domain.User;
import com.example.documentapproval.service.dto.LiquidatedPaymentDTO;
import com.example.documentapproval.service.dto.PaymentCommentDTO;

/**
 * 결제 승인 사용자 IF
 */
public interface PaymentCommentService {

  /**
   * 결제 승인 및 거절 로직
   *
   * @param dto  - 결제 승인 및 거절 DTO
   * @param user - 현재의 사용자 정보
   * @return PaymentCommentDTO
   */
  PaymentCommentDTO liquidateDocument(LiquidatedPaymentDTO dto, User user);
}
