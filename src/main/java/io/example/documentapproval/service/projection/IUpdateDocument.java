package io.example.documentapproval.service.projection;

import io.example.documentapproval.domain.PaymentUser;
import org.springframework.beans.factory.annotation.Value;

import java.util.Set;


public interface IUpdateDocument {

    Long getId();

    String getTitle();

    String getContent();

    @Value("#{target.classification.getId()}")
    Long getClassificationId();

    @Value("#{target.paymentUsers}")
    Set<PaymentUser> getPaymentUser();

}
