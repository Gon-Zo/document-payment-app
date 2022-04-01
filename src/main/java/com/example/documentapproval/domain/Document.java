package com.example.documentapproval.domain;

import com.example.documentapproval.enums.StateType;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Table
@Entity
@DynamicUpdate
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Document extends BaseTimeColumn {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Lob
  @Column(nullable = false)
  private String content;

  @OneToOne
  @JoinColumn(name = "division_code", nullable = false)
  private Division division;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false, name = "document_state")
  private StateType state;

  @Column(nullable = false, name = "document_step")
  private Integer step;

  @OneToMany(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
      mappedBy = "document")
  private Set<PaymentComment> paymentCommentSet = new HashSet<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Document document = (Document) o;
    return Objects.equals(id, document.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Builder(builderMethodName = "initBuilder", builderClassName = "initBuilder")
  private Document(Long id, String title, String content, User user, Division division) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.user = user;
    this.division = division;
  }

  @PrePersist
  @PostLoad
  void prePersist() {
    this.step = 1;
    this.state = StateType.DEFAULT;
  }

  public void approveState() {
    this.state = StateType.OK;
  }

  public void refuseState() {
    this.state = StateType.NO;
  }

  public void proceedState() {
    this.step += 1;
  }
}
