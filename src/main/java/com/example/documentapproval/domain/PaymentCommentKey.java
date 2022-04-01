package com.example.documentapproval.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Embeddable
@Builder(builderClassName = "allBuilder", builderMethodName = "allBuilder")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentCommentKey implements Serializable {

  private static final long serialVersionUID = 1L;

  @Column(nullable = false)
  private Long documentId;

  @Column(nullable = false)
  private Long userId;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PaymentCommentKey that = (PaymentCommentKey) o;
    return Objects.equals(documentId, that.documentId) && Objects.equals(userId, that.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(documentId, userId);
  }
}
