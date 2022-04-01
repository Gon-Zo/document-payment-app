package com.example.documentapproval.service;

import com.example.documentapproval.config.exception.NoDataException;
import com.example.documentapproval.domain.User;
import com.example.documentapproval.enums.MessageType;
import com.example.documentapproval.repository.UserRepository;
import com.example.documentapproval.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public SignUpUserDTO signupUser(UserDTO dto) {

    boolean isNotEmpty = !userRepository.existsByEmail(dto.getEmail());

    if (isNotEmpty) {

      final String encodePwd = passwordEncoder.encode(dto.getPassword());

      User entity = dto.toEntity(encodePwd);

      userRepository.save(entity);

      return SignUpUserDTO.builder()
          .id(entity.getId())
          .email(entity.getEmail())
          .role(entity.getRole())
          .createdDate(entity.getCreatedDate())
          .updatedDate(entity.getUpdatedDate())
          .build();
    }
    throw new NoDataException(MessageType.ExistsEmail);
  }
}
