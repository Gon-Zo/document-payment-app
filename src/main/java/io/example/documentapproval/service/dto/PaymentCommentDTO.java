package io.example.documentapproval.service.dto;

import io.example.documentapproval.domain.PaymentComment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentCommentDTO {

    private Boolean isOk;

    private String comment;

    public PaymentComment toEntity() {
        return PaymentComment.builder()
                .signYn(this.isOk)
                .comment(this.comment)
                .build();
    }

    public Boolean isNotSign() {
        return Boolean.FALSE.equals(this.isOk);
    }

}
