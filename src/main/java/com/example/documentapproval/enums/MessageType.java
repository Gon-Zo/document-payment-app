package com.example.documentapproval.enums;

/** 데이터 */
public enum MessageType {
  NoRoleData("M001", "role enum data null"),
  NoBoxTypeData("M002", "box type is null "),
  NoSelectedDivisionData("M003", "master division data not found"),
  NoPaymentCommentUser("M004", "document's payment comment is empty data"),
  EmptyStateType("M005", "state type is null"),
  NoSecurityUtilsData("M006", "security utils data not empty"),
  ExistsEmail("M007", "exists email"),
  EmptyDocumentOne("M008", "document data is not found id");

  private final String code;
  private final String message;

  MessageType(String code, String message) {
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
