package com.example.documentapproval.web;

import com.example.documentapproval.domain.Document;
import com.example.documentapproval.domain.User;
import com.example.documentapproval.service.DocumentService;
import com.example.documentapproval.service.PaymentCommentService;
import com.example.documentapproval.service.dto.*;
import com.example.documentapproval.utils.SecurityUtils;
import com.example.documentapproval.web.dosc.DocumentResourceDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DocumentResource implements DocumentResourceDocs {

  private final DocumentService documentService;

  private final PaymentCommentService paymentCommentService;

  @PostMapping("/document")
  public ResponseEntity<Document> savedDocument(@RequestBody @Valid DocumentDTO dto) {
    User user = SecurityUtils.getLoginUser();
    Document body = documentService.createdDocument(dto, user);
    return ResponseEntity.ok(body);
  }

  @GetMapping("/documents")
  public ResponseEntity<Page<DocumentInfo>> getDocuments(DocumentBoxDTO dto) {
    User user = SecurityUtils.getLoginUser();
    Page<DocumentInfo> body = documentService.getByDocumentList(dto, user);
    return ResponseEntity.ok(body);
  }

  @PutMapping("/document/{documentId}/payment-comment")
  public ResponseEntity<PaymentCommentDTO> liquidatePaymentComment(
      @PathVariable Long documentId, @Valid @RequestBody LiquidatedPaymentDTO dto) {

    dto.setDocumentId(documentId);

    User user = SecurityUtils.getLoginUser();

    PaymentCommentDTO body = paymentCommentService.liquidateDocument(dto, user);

    return ResponseEntity.ok(body);
  }

  @GetMapping("/document/{id}")
  @Override
  public ResponseEntity<IDocument> showDocumentOne(@PathVariable Long id) {
    IDocument body = documentService.getDocumentOne(id);
    return ResponseEntity.ok(body);
  }
}
