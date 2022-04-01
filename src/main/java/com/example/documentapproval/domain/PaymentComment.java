package com.example.documentapproval.domain;

import com.example.documentapproval.enums.StateType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Table
@Entity
@Builder(builderClassName = "allBuilder", builderMethodName = "allBuilder")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentComment {

  @EmbeddedId private PaymentCommentKey id;

  @JsonIgnore
  @ManyToOne(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.REMOVE})
  @MapsId("documentId")
  @JoinColumn(
      name = "document_id",
      referencedColumnName = "id",
      insertable = false,
      updatable = false)
  private Document document;

  @JsonIgnore
  @ManyToOne(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.REMOVE})
  @MapsId("userId")
  @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
  private User user;

  @Column(nullable = false, name = "payment_state")
  private StateType state;

  @Column(nullable = false, name = "payment_step")
  private Integer step;

  @Lob private String comment;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PaymentComment that = (PaymentComment) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Builder(builderClassName = "initBuilder", builderMethodName = "initBuilder")
  private PaymentComment(Long documentId, Long userId, Integer step) {
    this.id = PaymentCommentKey.allBuilder().documentId(documentId).userId(userId).build();
    this.step = step;
  }

  @PostLoad
  @PrePersist
  void prePersist() {
    this.state = StateType.DEFAULT;
  }

  @Transient
  public void updateCommentAndState(String comment, StateType state) {
    this.state = state;
    this.comment = comment;
  }
}
