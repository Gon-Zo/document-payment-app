package com.example.documentapproval.enums;

/** 로그인 관련 에러 메세지 enum */
public enum ErrorType {
  AUTHENTICATION_ERROR("A001", "authentication error"),
  INTERNAL_SERVER_ERROR("S001", "internal server error"),
  EMPTY_TOKEN_DOMAIN("S002", "token empty"),
  NOT_MATCH_TOKEN("S003", "token is not match"),
  JWT_UNSUPPORTED("J001", "jwt not supported"),
  JWT_MALFORMED("J002", "jwt malformed"),
  JWT_EXPIRED("J003", "jwt expired"),
  JWT_SIGNATURE("J003", "jwt signature");

  private final String code;

  private final String message;

  ErrorType(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
