package com.example.documentapproval.service.dto;

import com.example.documentapproval.enums.StateType;
import org.springframework.beans.factory.annotation.Value;

public interface IPaymentComment {

  @Value("#{target.id.userId}")
  Long getUserId();

  @Value("#{target.state}")
  StateType getState();

  String getComment();

  Integer getStep();
}
