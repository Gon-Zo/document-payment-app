package io.example.documentapproval.service.projection;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Set;

public interface IDocumentComment {

    Long getId();

    String getTitle();

    String getContent();

    @Value("#{target.classification.getTitle()}")
    String getClassification();

    @Value("#{target.user.getEmail()}")
    String getWirter();

    @Value("#{target.state.getCode()}")
    String getState();

    Date getCreateDate();

    Date getUpdateDate();

    @Value("#{target.paymentUsers}")
    Set<IPaymentUser> getPaymentUsers();

    interface IPaymentUser {

        Long getId();

        Long getStep();

        @Value("#{target.user.getEmail()}")
        String getEmail();

        @Value("#{target.paymentComment.getSignYn()}")
        String signYn();

        @Value("#{target.paymentComment.comment}")
        String getComment();

    }

}
