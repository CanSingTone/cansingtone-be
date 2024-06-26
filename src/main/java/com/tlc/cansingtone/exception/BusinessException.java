package com.tlc.cansingtone.exception;
import com.tlc.cansingtone.exception.ErrorCode;

public class BusinessException extends RuntimeException {
    private ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }


    public ErrorCode getErrorCode() {
        return errorCode;
    }
}