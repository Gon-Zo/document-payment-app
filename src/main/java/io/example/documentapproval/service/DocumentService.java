package io.example.documentapproval.service;

import io.example.documentapproval.domain.*;
import io.example.documentapproval.enums.ErrorCode;
import io.example.documentapproval.repository.ClassificationRepository;
import io.example.documentapproval.repository.DocumentRepository;
import io.example.documentapproval.repository.UserRepository;
import io.example.documentapproval.service.dto.DocumentDTO;
import io.example.documentapproval.service.dto.PaymentCommentDTO;
import io.example.documentapproval.service.excpetion.AppException;
import io.example.documentapproval.service.projection.*;
import io.example.documentapproval.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;

    private final ClassificationRepository classificationRepository;

    private final UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public IDocumentComment getDocumentPaymentComment(Long id) {
        return documentRepository.findById(id, IDocumentComment.class).orElseThrow(() -> new AppException(ErrorCode.EMPTY_DOCUMENT));
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public IDocument getDocumentDetail(Long id) {
        return documentRepository.findById(id, IDocument.class).orElseThrow(() -> new AppException(ErrorCode.EMPTY_DOCUMENT));
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public IUpdateDocument getUpdateDocument(Long id) {
        return documentRepository.findById(id, IUpdateDocument.class).orElseThrow(() -> new AppException(ErrorCode.EMPTY_DOCUMENT));
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<IUserIdEmail> getUserList() {
        return userRepository.findAllProjectedBy(IUserIdEmail.class);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<IClassification> getClassificationList() {
        return classificationRepository.findAllProjectedBy(IClassification.class);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateDocument(Long id, DocumentDTO dto) {

        Document entity = documentRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.EMPTY_DOCUMENT));

        Classification classification = getClassification(dto.getClassificationId());

        Set<PaymentUser> paymentUsers = getPaymentUserList(dto.getUserIds(), entity);

        entity.updateDocument(dto.getTitle(), dto.getContent(), paymentUsers, classification);

    }

    @Transactional(rollbackFor = Exception.class)
    public void createdDocument(DocumentDTO dto) {

        Classification classification = getClassification(dto.getClassificationId());

        User loginUser = userRepository.findById(SecurityUtils.getLoginUserId()).orElseThrow(() -> new AppException(ErrorCode.EMPTY_LOGIN_USER));

        Document entity = dto.toEntity(classification, loginUser);

        documentRepository.save(entity);

        Set<PaymentUser> paymentUsers = getPaymentUserList(dto.getUserIds(), entity);

        entity.createdPaymentStep(paymentUsers);

        entity.waitedPayment();

        entity.startStep();

    }

    @Transactional(rollbackFor = Exception.class)
    public void signDocument(Long documentId, PaymentCommentDTO dto) {

        Document document = documentRepository.findById(documentId).orElseThrow(() -> new AppException(ErrorCode.EMPTY_DOCUMENT));

        Set<PaymentUser> paymentUsers = document.getPaymentUsers();

        if (CollectionUtils.isEmpty(paymentUsers)) {
            throw new AppException(ErrorCode.NOT_FOUND_PAYMENTUSERS);
        }

        PaymentUser foundPaymentUser = paymentUsers.stream().filter(commentUser -> commentUser.getUser().getId().equals(SecurityUtils.getLoginUserId())).findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_PAYMENTUSER));

        PaymentComment commentData = dto.toEntity();

        foundPaymentUser.liquidateDocument(commentData);

        PaymentStep documentStep = document.getStep();

        PaymentState paymentState = document.getState();

        if (dto.isNotSign()) {
            paymentState.rejectDocument();
            document.rejectDocument();
            return;
        }

        long paymentCommentCount = paymentUsers.stream().map(PaymentUser::getPaymentComment).filter(Objects::nonNull).count();

        if (paymentCommentCount == paymentUsers.size()) {
            paymentState.approveDocument();
        } else {
            documentStep.stepUp();
        }

    }

    private Classification getClassification(Long classificationId) {
        return classificationRepository.findById(classificationId)
                .orElseThrow(() -> new AppException(ErrorCode.EMPTY_CLASSIFICATION));
    }

    private Set<PaymentUser> getPaymentUserList(Set<Long> userIds, Document entity) {

        List<User> users = userRepository.findByIdIn(userIds);

        if (CollectionUtils.isEmpty(users)) {
            throw new AppException(ErrorCode.EMPTY_PAYMENT_USER);
        }

        return IntStream.range(0, users.size())
                .mapToObj(num -> PaymentUser.builder()
                        .step(num)
                        .document(entity)
                        .user(users.get(num))
                        .build())
                .collect(Collectors.toSet());
    }

}
