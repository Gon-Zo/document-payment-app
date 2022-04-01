package com.example.documentapproval.service;

import com.example.documentapproval.repository.DivisionRepository;
import com.example.documentapproval.service.dto.IDivision;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DivisionServiceImpl implements DivisionService {

  private final DivisionRepository divisionRepository;

  @Override
  @Transactional(rollbackFor = Exception.class, readOnly = true)
  public List<IDivision> getDivisionList() {
    return divisionRepository.findAllProjectedBy(IDivision.class);
  }
}
