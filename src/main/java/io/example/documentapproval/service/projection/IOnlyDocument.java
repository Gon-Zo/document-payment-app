package io.example.documentapproval.service.projection;

import org.springframework.beans.factory.annotation.Value;

public interface IOnlyDocument {

    @Value("#{target.getDocument()}")
    IDocumentBox getDocument();

}
