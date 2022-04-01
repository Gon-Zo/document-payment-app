package com.example.documentapproval.repository.support;

import com.example.documentapproval.repository.support.boxaction.BoxActionFactory;
import com.example.documentapproval.service.dto.DocumentInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DocumentCustomRepository {

  /**
   * list up querydsl
   *
   * @param pageable - 페이징 객제
   * @param factory - 조건별 where query 객체
   * @return Page<DocumentInfo>
   */
  Page<DocumentInfo> findByBoxAction(Pageable pageable, BoxActionFactory factory);
}
