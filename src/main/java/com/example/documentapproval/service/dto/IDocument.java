package com.example.documentapproval.service.dto;

import com.example.documentapproval.enums.StateType;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Set;

public interface IDocument {

  Long getId();

  String getTitle();

  String getContent();

  @Value("#{target.division.name}")
  String getDivisionName();

  StateType getState();

  Integer getStep();

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  Date getCreatedDate();

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  Date getUpdatedDate();

  @Value("#{target.user.email}")
  String getWriter();

  Set<IPaymentComment> getPaymentCommentSet();
}
