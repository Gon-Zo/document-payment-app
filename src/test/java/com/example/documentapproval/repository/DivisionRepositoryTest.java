package com.example.documentapproval.repository;

import com.example.documentapproval.config.InitConfiguration;
import com.example.documentapproval.config.QueryDslConfiguration;
import com.example.documentapproval.domain.Division;
import com.example.documentapproval.mock.DivisionMock;
import com.example.documentapproval.service.dto.IDivision;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@Import(value = {QueryDslConfiguration.class, InitConfiguration.class})
class DivisionRepositoryTest {

  @Autowired private DivisionRepository divisionRepository;

  private List<Division> divisions;

  @Nested
  class Select {

    @BeforeEach
    void init() {
      divisions = divisionRepository.saveAll(DivisionMock.createMasterDivision());

      divisionRepository.flush();
    }

    @Test
    @DisplayName("마스터 분류 전체 로직")
    void findAllProjectedBy() {

      List<IDivision> entities = divisionRepository.findAllProjectedBy(IDivision.class);

      entities.forEach(
          entity -> {
            Division checkedMock =
                divisions.stream()
                    .filter(division -> division.getCode().equals(entity.getCode()))
                    .findFirst()
                    .orElseThrow();

            org.junit.jupiter.api.Assertions.assertEquals(entity.getName(), checkedMock.getName());
          });
    }

    @Test
    @DisplayName("코드 찾는 로직 테스트 케이스")
    void findByCode() {

      Division mock = divisions.get(0);

      Optional<Division> entityOptional = divisionRepository.findById(mock.getCode());

      org.junit.jupiter.api.Assertions.assertTrue(entityOptional.isPresent());

      Division entity = entityOptional.get();

      Assertions.assertThat(entity).isEqualTo(mock);

      org.junit.jupiter.api.Assertions.assertEquals(entity.getCode(), mock.getCode());
      org.junit.jupiter.api.Assertions.assertEquals(entity.getName(), mock.getName());
    }
  }
}
