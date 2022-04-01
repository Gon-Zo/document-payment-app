package com.example.documentapproval.service.dto;

import com.example.documentapproval.domain.Division;
import com.example.documentapproval.domain.Document;
import com.example.documentapproval.domain.PaymentComment;
import com.example.documentapproval.domain.User;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@Builder(builderMethodName = "allBuilder", builderClassName = "allBuilder")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DocumentDTO {

  @NotEmpty private String title;

  @NotEmpty private String content;

  @NotEmpty private String divisionCode;

  @NotEmpty private List<Integer> userIdList;

  public Document toEntity(User user) {
    return Document.initBuilder()
        .title(title)
        .content(content)
        .user(user)
        .division(new Division(divisionCode))
        .build();
  }

  /**
   * 결제 해야할 유저 리스트 업
   *
   * @param documentId
   * @return
   */
  public List<PaymentComment> getPaymentCommentList(Long documentId) {
    int userIdListSize = userIdList.size();
    return IntStream.range(0, userIdListSize)
        .mapToObj(
            num -> {
              Integer userId = userIdList.get(num);
              return PaymentComment.initBuilder()
                  .documentId(documentId)
                  .userId(userId.longValue())
                  .step(num + 1)
                  .build();
            })
        .collect(Collectors.toList());
  }
}
