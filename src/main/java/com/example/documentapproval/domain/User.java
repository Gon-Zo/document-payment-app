package com.example.documentapproval.domain;

import com.example.documentapproval.config.exception.NoDataException;
import com.example.documentapproval.domain.convert.RoleEnumConvert;
import com.example.documentapproval.enums.MessageType;
import com.example.documentapproval.enums.RoleEnum;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Optional;

@Table
@Getter
@Entity
@Builder(builderClassName = "allBuilder", builderMethodName = "allBuilder")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeColumn {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false, name = "hash_password")
  private String password;

  @Convert(converter = RoleEnumConvert.class)
  @Column(nullable = false, name = "role_name")
  private RoleEnum role;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(id, user.id)
        && Objects.equals(email, user.email)
        && Objects.equals(password, user.password)
        && role == user.role;
  }

  @Builder(builderClassName = "loginUserBuilder", builderMethodName = "loginUserBuilder")
  private User(Long id, String email, String role) {
    this.id = id;
    this.email = email;
    String roleStr =
        Optional.ofNullable(role).orElseThrow(() -> new NoDataException(MessageType.NoRoleData));
    this.role = RoleEnum.valueOf(roleStr);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, email, password, role);
  }
}
