package com.gaaji.chat.execption;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AbstractApiException extends RuntimeException implements ErrorCode {
    protected HttpStatus httpStatus;
    protected String errorCode;
    protected String errorName;
    protected String errorMessage;

    public AbstractApiException (ErrorCode errorCode) {

        httpStatus = errorCode.getHttpStatus();
        this.errorCode = errorCode.getErrorCode();
        errorName = errorCode.getErrorName();
        errorMessage = errorCode.getErrorMessage();
    }
}
