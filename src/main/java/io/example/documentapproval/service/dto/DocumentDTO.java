package io.example.documentapproval.service.dto;

import io.example.documentapproval.domain.Classification;
import io.example.documentapproval.domain.Document;
import io.example.documentapproval.domain.User;
import io.example.documentapproval.enums.ErrorCode;
import io.example.documentapproval.service.excpetion.AppException;
import io.example.documentapproval.service.projection.IUpdateDocument;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
public class DocumentDTO {

    private String title;

    private String content;

    private Long classificationId;

    private Set<Long> userIds;

    public Document toEntity(Classification classification, User user) {
        return Document.builder()
                .title(this.title)
                .content(this.content)
                .user(user)
                .classification(classification)
                .build();
    }

    public static DocumentDTO convertOf(IUpdateDocument document) {
        Set<Long> userIds = document.getPaymentUser().stream()
                .map((paymentUser) -> {
                    if (ObjectUtils.isNotEmpty(paymentUser.getPaymentComment())) {
                        throw new AppException(ErrorCode.PAYMENTING_DOCUMNET);
                    }
                    return paymentUser.getUser().getId();
                })
                .collect(Collectors.toSet());
        return new DocumentDTO(document.getTitle(), document.getContent(), document.getClassificationId(), userIds);
    }

    public DocumentDTO(String title, String content, Long classificationId, Set<Long> userIds) {
        this.title = title;
        this.content = content;
        this.classificationId = classificationId;
        this.userIds = userIds;
    }

}
