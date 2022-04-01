package com.example.documentapproval.service.dto;

import com.example.documentapproval.domain.Document;
import com.example.documentapproval.enums.StateType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
public class DocumentInfo {

  private Long id;

  private String title;

  private String content;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  private Date createdDate;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  private Date updatedDate;

  private String writer;

  private String divisionName;

  private StateType state;

  private Integer step;

  public DocumentInfo setDocumentInfo(
      Document document) {
    this.id = document.getId();
    this.title = document.getTitle();
    this.content = document.getContent();
    this.createdDate = document.getCreatedDate();
    this.updatedDate = document.getUpdatedDate();
    this.writer = document.getUser().getEmail();
    this.divisionName = document.getDivision().getName();
    this.state = document.getState();
    this.step = document.getStep();
    return this;
  }
}
