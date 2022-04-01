package com.example.documentapproval.service;

import com.example.documentapproval.config.exception.NoDataException;
import com.example.documentapproval.domain.User;
import com.example.documentapproval.mock.UserMock;
import com.example.documentapproval.repository.UserRepository;
import com.example.documentapproval.service.dto.UserDTO;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceTest {

  @Mock private UserRepository userRepository;

  @Mock private PasswordEncoder passwordEncoder;

  private UserService userService;

  @BeforeEach
  void init() {
    MockitoAnnotations.openMocks(this);
    userService = new UserServiceImpl(userRepository, passwordEncoder);
  }

  @Test
  @DisplayName("회원 가입 로직")
  void singIn() {

    User mock = UserMock.createUserNotAdmin();

    boolean isMockAble = false;

    BDDMockito.given(userRepository.existsByEmail(any())).willReturn(isMockAble);

    BDDMockito.given(userRepository.save(any())).willReturn(mock);

    UserDTO dto = UserMock.createUserDTO();

    SignUpUserDTO entity = userService.signupUser(dto);

    BDDMockito.then(userRepository).should().existsByEmail(any());

    BDDMockito.then(userRepository).should().save(any());

    Assertions.assertEquals(mock.getEmail(), entity.getEmail());
    Assertions.assertEquals(mock.getRole(), entity.getRole());
  }

  @Test
  @DisplayName("회원 가입 로직 같은 아이디 테스트 케이스")
  void singIn_exists() {

    User mock = UserMock.createUserNotAdmin();

    boolean isMockAble = true;

    BDDMockito.given(userRepository.existsByEmail(any())).willReturn(isMockAble);

    BDDMockito.given(userRepository.save(any())).willReturn(mock);

    UserDTO dto = UserMock.createUserDTO();

    assertThrows(NoDataException.class, () -> userService.signupUser(dto));
  }
}
