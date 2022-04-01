package com.example.documentapproval.service;

import com.example.documentapproval.domain.Division;
import com.example.documentapproval.mock.DivisionMock;
import com.example.documentapproval.repository.DivisionRepository;
import com.example.documentapproval.service.dto.IDivision;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DivisionServiceTest {

  private DivisionService divisionService;

  @Mock private DivisionRepository divisionRepository;

  @BeforeEach
  void init() {
    MockitoAnnotations.openMocks(this);
    divisionService = new DivisionServiceImpl(divisionRepository);
  }

  @Test
  @DisplayName("마스터 분류 서비스 로직 - 전체 불러오기")
  void getDivisionList() {

    List<IDivision> mockList = DivisionMock.createdDivisionList();

    BDDMockito.given(divisionRepository.findAllProjectedBy(eq(IDivision.class)))
        .willReturn(mockList);

    List<IDivision> entities = divisionService.getDivisionList();

    mockList.forEach(
        mock -> {
          IDivision value =
              entities.stream()
                  .filter(entity -> entity.getCode().equals(mock.getCode()))
                  .findFirst()
                  .orElseThrow();

          Assertions.assertEquals(value.getName(), mock.getName());
        });
  }
}
