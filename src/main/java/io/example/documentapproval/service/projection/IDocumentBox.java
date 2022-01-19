package io.example.documentapproval.service.projection;


import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface IDocumentBox {

    Long getId();

    String getTitle();

    @Value("#{target.classification.getTitle()}")
    String getClassification();

    @Value("#{target.user.getEmail()}")
    String getWirter();

    @Value("#{target.state.getCode()}")
    String getState();

    Date getCreateDate();

    Date getUpdateDate();

}
