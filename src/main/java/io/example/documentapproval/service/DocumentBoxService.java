package io.example.documentapproval.service;

import io.example.documentapproval.enums.StateCode;
import io.example.documentapproval.repository.DocumentRepository;
import io.example.documentapproval.repository.PaymentUserRepository;
import io.example.documentapproval.service.projection.IDocumentBox;
import io.example.documentapproval.service.projection.IOnlyDocument;
import io.example.documentapproval.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static io.example.documentapproval.utils.AppUtils.distinctByKey;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class DocumentBoxService {

    private final DocumentRepository documentRepository;

    private final PaymentUserRepository paymentUserRepository;

    public List<IDocumentBox> getOutBoxDocumentList() {
        return documentRepository.findByUser_IdAndState_Code(SecurityUtils.getLoginUserId(), StateCode.WAIT, IDocumentBox.class);
    }

    public List<IDocumentBox> getArchiveDocumentList() {
        return paymentUserRepository.selectArchiveDocumentList(SecurityUtils.getLoginUserId(), Arrays.asList(StateCode.OK, StateCode.NO), IOnlyDocument.class)
                .stream()
                .map(IOnlyDocument::getDocument)
                .filter(distinctByKey(IDocumentBox::getId))
                .collect(Collectors.toList());
    }

    public List<IDocumentBox> getInBoxDocumentList() {
        return paymentUserRepository.selectInBoxDocumentList(SecurityUtils.getLoginUserId(), StateCode.WAIT, IOnlyDocument.class)
                .stream()
                .map(IOnlyDocument::getDocument)
                .collect(Collectors.toList());
    }

}
