package com.example.documentapproval.repository;

import com.example.documentapproval.config.InitConfiguration;
import com.example.documentapproval.config.QueryDslConfiguration;
import com.example.documentapproval.domain.User;
import com.example.documentapproval.mock.UserMock;
import com.example.documentapproval.service.dto.IUser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@DataJpaTest
@DisplayName("유저 로직")
@ExtendWith(SpringExtension.class)
@Import(value = {QueryDslConfiguration.class, InitConfiguration.class})
class UserRepositoryTest {

  @Autowired private UserRepository userRepository;

  @BeforeEach
  void init() {}

  @Test
  @DisplayName("회원 가입 로직")
  void save() throws Exception {
    User mock = UserMock.createUser();

    User entity = userRepository.save(mock);

    Assertions.assertThat(mock).isEqualTo(entity);

    org.junit.jupiter.api.Assertions.assertEquals(mock.getId(), entity.getId());
    org.junit.jupiter.api.Assertions.assertEquals(mock.getEmail(), entity.getEmail());
    org.junit.jupiter.api.Assertions.assertEquals(mock.getPassword(), entity.getPassword());
    org.junit.jupiter.api.Assertions.assertEquals(mock.getRole(), entity.getRole());
  }

  @Test
  @DisplayName("회원 가입 로직 - Role 비었을떄")
  void save_empty_role() throws Exception {
    User mock = UserMock.createUserEmptyRole();
    org.junit.jupiter.api.Assertions.assertThrows(
        JpaSystemException.class, () -> userRepository.save(mock));
  }

  @Nested
  class Select {

    User mock;

    @BeforeEach
    void init() {
      // given
      mock = userRepository.save(UserMock.createUser());
    }

    @Test
    @DisplayName("이메일로 조회 테스트 케이스")
    void findByEmail() throws Exception {

      Optional<User> entityOptional = userRepository.findByEmail(mock.getEmail());

      org.junit.jupiter.api.Assertions.assertTrue(entityOptional.isPresent());

      User entity = entityOptional.get();

      Assertions.assertThat(entity).isEqualTo(mock);
      org.junit.jupiter.api.Assertions.assertEquals(mock.getId(), entity.getId());
      org.junit.jupiter.api.Assertions.assertEquals(mock.getEmail(), entity.getEmail());
      org.junit.jupiter.api.Assertions.assertEquals(mock.getPassword(), entity.getPassword());
      org.junit.jupiter.api.Assertions.assertEquals(mock.getRole(), entity.getRole());
    }

    @Test
    @DisplayName("이메일로 조회 프로젝션 테스트 케이스")
    void findByEmail_projection() throws Exception {

      Optional<IUser> entityOptional = userRepository.findByEmail(mock.getEmail(), IUser.class);

      org.junit.jupiter.api.Assertions.assertTrue(entityOptional.isPresent());

      IUser entity = entityOptional.get();
      org.junit.jupiter.api.Assertions.assertEquals(mock.getId(), entity.getId());
      org.junit.jupiter.api.Assertions.assertEquals(mock.getEmail(), entity.getEmail());
      org.junit.jupiter.api.Assertions.assertEquals(mock.getPassword(), entity.getPassword());
      org.junit.jupiter.api.Assertions.assertEquals(mock.getRole(), entity.getRole());
    }

    @Test
    @DisplayName("이메일 중복 체크 테스트 케이스")
    void existsByEmail() {

      boolean isExists = userRepository.existsByEmail(mock.getEmail());

      org.junit.jupiter.api.Assertions.assertTrue(isExists);
    }

    @Test
    @DisplayName("이메일 중복 체크 실패 테스트 케이스")
    void existsByEmail_false() {

      String email = "email@naver.com";

      boolean isExists = userRepository.existsByEmail(email);

      org.junit.jupiter.api.Assertions.assertFalse(isExists);
    }
  }
}
