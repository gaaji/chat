package com.gaaji.chat.execption;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class AbstractServerException extends RuntimeException {
    protected HttpStatus httpStatus;
    protected String code;
    protected String errorName;
    protected String errorMessage;

    @Override
    public String toString() {
        return "ServerException{" +
                "httpStatus=" + httpStatus +
                ", errorCode='" + code + '\'' +
                ", errorName='" + errorName + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
