package com.example.documentapproval.service.dto;

import com.example.documentapproval.enums.StateType;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Builder(builderMethodName = "allBuilder", builderClassName = "allBuilder")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LiquidatedPaymentDTO {
  @Setter private Long documentId;

  @NotEmpty private String comment;

  @NotNull private StateType state;
}
