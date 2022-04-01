package com.example.documentapproval.service;

import com.example.documentapproval.config.exception.NoDataException;
import com.example.documentapproval.domain.Document;
import com.example.documentapproval.domain.User;
import com.example.documentapproval.enums.MessageType;
import com.example.documentapproval.repository.DocumentRepository;
import com.example.documentapproval.repository.support.boxaction.BoxActionFactory;
import com.example.documentapproval.service.dto.DocumentBoxDTO;
import com.example.documentapproval.service.dto.DocumentDTO;
import com.example.documentapproval.service.dto.DocumentInfo;
import com.example.documentapproval.service.dto.IDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

  private final DocumentRepository documentRepository;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Document createdDocument(DocumentDTO dto, User user) {

    Document entity = dto.toEntity(user);

    documentRepository.save(entity);

    entity.getPaymentCommentSet().addAll(dto.getPaymentCommentList(entity.getId()));

    return entity;
  }

  @Override
  @Transactional(readOnly = true, rollbackFor = Exception.class)
  public Page<DocumentInfo> getByDocumentList(DocumentBoxDTO dto, User user) {

    // page dto
    PageRequest pageable = PageRequest.of(dto.getPage(), dto.getSize());

    // 결제 문서함 별로 query where 변경
    BoxActionFactory factory = new BoxActionFactory().getBoxAction(dto.getType(), user.getId());

    return documentRepository.findByBoxAction(pageable, factory);
  }

  @Override
  @Transactional(rollbackFor = Exception.class, readOnly = true)
  public IDocument getDocumentOne(Long id) {
    return documentRepository
        .findById(id, IDocument.class)
        .orElseThrow(() -> new NoDataException(MessageType.EmptyDocumentOne));
  }
}
