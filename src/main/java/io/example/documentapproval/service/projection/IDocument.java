package io.example.documentapproval.service.projection;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface IDocument {

    Long getId();

    String getTitle();

    String getContent();

    @Value("#{target.classification.getId()}")
    Long getClassificationId();

    @Value("#{target.classification.getTitle()}")
    String getClassification();

    @Value("#{target.user.getEmail()}")
    String getWirter();

    @Value("#{target.state.getCode()}")
    String getState();

    Date getCreateDate();

    Date getUpdateDate();

}
