package io.example.documentapproval.service.excpetion;

import io.example.documentapproval.enums.ErrorCode;

public class AppException extends RuntimeException {

    private ErrorCode errorCode;

    public AppException() {
    }

    public AppException(ErrorCode errorCode) {
        super(errorCode.getValue());
        this.errorCode = errorCode;
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(Throwable cause) {
        super(cause);
    }

    public AppException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
