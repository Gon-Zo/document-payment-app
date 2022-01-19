package io.example.documentapproval.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorCodeDTO {

    private String code;

    private String message;

    @Builder
    public ErrorCodeDTO(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
