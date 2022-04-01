package com.example.documentapproval.mock;

import com.example.documentapproval.domain.Division;
import com.example.documentapproval.service.dto.IDivision;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DivisionMock {

  public static List<Division> createMasterDivision() {
    return List.of(
        new Division("c001", "분류"),
        new Division("c002", "분류2"),
        new Division("c003", "분류3"),
        new Division("c004", "분류4"),
        new Division("c005", "분류5"));
  }

  public static Division createMock() {
    return new Division("c001", "test1");
  }

  public static Optional<Division> createMockToOptional() {
    return Optional.of(new Division("c001", "test1"));
  }

  public static List<IDivision> createdDivisionList() {
    return createMasterDivision().stream().map(DivisionMockValue::new).collect(Collectors.toList());
  }

  static class DivisionMockValue implements IDivision {

    private final String code;

    private final String name;

    DivisionMockValue(Division division) {
      this.code = division.getCode();
      this.name = division.getName();
    }

    @Override
    public String getCode() {
      return this.code;
    }

    @Override
    public String getName() {
      return this.name;
    }
  }
}
