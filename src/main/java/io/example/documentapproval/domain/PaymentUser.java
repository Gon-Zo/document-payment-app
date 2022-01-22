package io.example.documentapproval.domain;

import io.example.documentapproval.domain.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;

@Getter
@Table
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentUser extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer step;

    @ManyToOne
    @JoinColumn(name = "document_id", referencedColumnName = "id")
    private Document document;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(mappedBy = "paymentUser", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private PaymentComment paymentComment;

    @Builder
    public PaymentUser(Integer step, Document document, User user) {
        this.step = step;
        this.document = document;
        this.user = user;
    }

    @Transient
    public void liquidateDocument(PaymentComment paymentComment) {
        this.paymentComment = paymentComment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentUser) || Hibernate.getClass(o) != Hibernate.getClass(this)) return false;
        PaymentUser that = (PaymentUser) o;
        return that.getId().equals(this.getId());
    }

    @Override
    public int hashCode() {
        return id.intValue();
    }
}
