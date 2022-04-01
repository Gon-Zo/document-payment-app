package com.example.documentapproval.service.dto;

import com.example.documentapproval.enums.BoxType;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DocumentBoxDTO {

  @NotNull private BoxType type;

  @NotNull private Integer page;

  @NotNull private Integer size;
}
