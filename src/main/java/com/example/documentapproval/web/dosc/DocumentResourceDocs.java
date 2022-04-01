package com.example.documentapproval.web.dosc;

import com.example.documentapproval.domain.Document;
import com.example.documentapproval.service.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface DocumentResourceDocs {

  @Operation(summary = "결재 문서 생성 API")
  ResponseEntity<Document> savedDocument(DocumentDTO dto);

  @Operation(summary = "결제 함 리스트 업 API")
  ResponseEntity<Page<DocumentInfo>> getDocuments(DocumentBoxDTO dto);

  @Operation(summary = "결제 문서 승인 여부 API")
  ResponseEntity<PaymentCommentDTO> liquidatePaymentComment(
      Long documentId, LiquidatedPaymentDTO dto);

  @Operation(summary = "결제 문서 상세 API")
  ResponseEntity<IDocument> showDocumentOne(Long id);
}
