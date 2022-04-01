package com.example.documentapproval.web;

import com.example.documentapproval.service.DivisionService;
import com.example.documentapproval.service.dto.IDivision;
import com.example.documentapproval.web.dosc.DivisionResourceDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DivisionResource implements DivisionResourceDocs {

  private final DivisionService divisionService;

  @Override
  @GetMapping("/divisions")
  public ResponseEntity<List<IDivision>> showMasterDivisionList() {
    List<IDivision> body = divisionService.getDivisionList();
    return ResponseEntity.ok(body);
  }
}
