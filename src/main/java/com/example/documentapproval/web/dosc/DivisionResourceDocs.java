package com.example.documentapproval.web.dosc;

import com.example.documentapproval.service.dto.IDivision;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DivisionResourceDocs {

  @Operation(summary = "마스터 분류 전체 조회 API")
  ResponseEntity<List<IDivision>> showMasterDivisionList();
}
