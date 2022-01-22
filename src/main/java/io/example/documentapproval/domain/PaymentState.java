package io.example.documentapproval.domain;

import io.example.documentapproval.domain.base.BaseTimeEntity;
import io.example.documentapproval.domain.convert.StateCodeConvert;
import io.example.documentapproval.enums.StateCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Table
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentState extends BaseTimeEntity implements Serializable {

    @Transient
    private final Long serialVersionUID = 1L;

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", referencedColumnName = "id")
    private Document document;

    @Convert(converter = StateCodeConvert.class)
    @Column(name = "state_code", nullable = false)
    private StateCode code;

    @Builder
    public PaymentState(Document document, StateCode code) {
        this.document = document;
        this.code = code;
    }

    @Transient
    public void rejectDocument() {
        this.code = StateCode.NO;
    }

    @Transient
    public void approveDocument() {
        this.code = StateCode.OK;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentState) || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PaymentState that = (PaymentState) o;
        return that.getDocument().getId().equals(this.getDocument().getId());
    }

    @Override
    public int hashCode() {
        int result = getSerialVersionUID() != null ? getSerialVersionUID().hashCode() : 0;
        result = 31 * result + (getDocument() != null ? getDocument().hashCode() : 0);
        result = 31 * result + (getCode() != null ? getCode().hashCode() : 0);
        return result;
    }
}
