package io.example.documentapproval.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.example.documentapproval.domain.base.BaseTimeEntity;
import io.example.documentapproval.enums.StateCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Table
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Document extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classification_id")
    private Classification classification;

    @JsonIgnore
    @OneToOne(mappedBy = "document", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private PaymentState state;

    @JsonIgnore
    @OneToOne(mappedBy = "document", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private PaymentStep step;

    @JsonIgnore
    @OneToMany(mappedBy = "document", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Set<PaymentUser> paymentUsers = new HashSet<>();

    @Builder
    public Document(String title, String content, User user, Classification classification, PaymentState state, Set<PaymentUser> paymentUsers) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.classification = classification;
        this.state = state;
        this.paymentUsers = paymentUsers;
    }

    @Transient
    public void createdPaymentStep(Set<PaymentUser> paymentUsers) {
        this.paymentUsers = paymentUsers;
    }

    @Transient
    public void waitedPayment() {
        this.state = PaymentState.builder()
                .document(this)
                .code(StateCode.WAIT)
                .build();
    }

    @Transient
    public void startStep() {
        this.step = PaymentStep.builder().build();
    }

    @Transient
    public void updateDocument(String title, String content, Set<PaymentUser> paymentUsers, Classification classification) {
        this.title = title;
        this.content = content;
        this.paymentUsers = paymentUsers;
        this.classification = classification;
    }

    @Transient
    public void rejectDocument() {
        this.paymentUsers = this.paymentUsers.stream()
                .filter(paymentUser -> ObjectUtils.isNotEmpty(paymentUser.getPaymentComment()))
                .collect(Collectors.toSet());
    }

}
