package com.example.documentapproval.config;

import com.example.documentapproval.domain.Division;
import com.example.documentapproval.repository.DivisionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/** init 될때 필요한데이터 */
@Component
@RequiredArgsConstructor
public class InitConfiguration {

  private final DivisionRepository divisionRepository;

  public void init() {
    long count = divisionRepository.count();
    if (count == 0L) {
      List<Division> list =
          List.of(
              new Division("c001", "분류"),
              new Division("c002", "분류2"),
              new Division("c003", "분류3"),
              new Division("c004", "분류4"),
              new Division("c005", "분류5"));
      divisionRepository.saveAll(list);
    }
  }
}
