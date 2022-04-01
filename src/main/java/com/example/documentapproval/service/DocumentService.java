package com.example.documentapproval.service;

import com.example.documentapproval.domain.Document;
import com.example.documentapproval.domain.User;
import com.example.documentapproval.service.dto.DocumentBoxDTO;
import com.example.documentapproval.service.dto.DocumentDTO;
import com.example.documentapproval.service.dto.DocumentInfo;
import com.example.documentapproval.service.dto.IDocument;
import org.springframework.data.domain.Page;

import java.util.Optional;

/** 결제 서비스 IF */
public interface DocumentService {

  /**
   * 유저 저장
   *
   * @param dto - 결제 DTO
   * @param user - 현재 유저의 정보
   * @return Document
   */
  Document createdDocument(DocumentDTO dto, User user);

  /**
   * 결제함 문서 목록 리스트업
   *
   * @param dto - 결제 문서함 DTO
   * @param user - 로그인한 유저
   * @return Page<DocumentInfo>
   */
  Page<DocumentInfo> getByDocumentList(DocumentBoxDTO dto, User user);

  /**
   * 결제 문서 상세 데이터
   *
   * @param id - 결제 문서 데이터 id
   * @return IDocument
   */
  IDocument getDocumentOne(Long id);
}
