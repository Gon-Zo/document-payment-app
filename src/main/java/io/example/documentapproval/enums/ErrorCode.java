package io.example.documentapproval.enums;

public enum ErrorCode implements BaseEnum<String> {


    SERVER_ERROR("S001" , "서버 에러 발생이 되었습니다"),
    EMPTY_NOT_USERNAME("A001" , "로그인 유저가 없습니다"),
    EMPTY_CLASSIFICATION("D001", "마스터 분류가 선택되지 않았습니다"),
    EMPTY_PAYMENT_USER("D002", "결재자가 선택되지 않았습니다"),
    EMPTY_LOGIN_USER("D003" , "없는 유저 입니다"),
    EMPTY_STATE_CODE("D004" , "문서의 상태코드가 없습니다"),
    EMPTY_DOCUMENT("D005" , "없는 문서입니다"),
    NOT_FOUND_PAYMENTUSER("D006" , "찾을 수 없는 결재자 입니다"),
    NOT_FOUND_PAYMENTUSERS("D007" , "결재자가 없습니다"),
    PAYMENTING_DOCUMNET("D008" , "이미 결재가 진행중입니다")
    ;

    private String code;

    private String value;

    ErrorCode(String code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public String getCode() {
        return code;
    }

}
