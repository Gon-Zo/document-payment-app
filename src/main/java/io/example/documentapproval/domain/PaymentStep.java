package io.example.documentapproval.domain;

import io.example.documentapproval.domain.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.*;
import java.io.Serializable;

@Table
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentStep extends BaseTimeEntity implements Serializable {

    @Transient
    private final Long serialVersionUID = 1L;

    @Id
    @OneToOne
    @JoinColumn(name = "document_id", referencedColumnName = "id")
    private Document document;

    @Column(nullable = false)
    private Integer step;

    @PrePersist
    @PostLoad
    void onInsert() {
        if (ObjectUtils.isEmpty(this.step)) step = 0;
    }

    @Builder
    public PaymentStep(Document document, Integer step) {
        this.document = document;
        this.step = step;
    }

    @Transient
    public void stepUp() {
        this.step += 1 ;
    }

}
