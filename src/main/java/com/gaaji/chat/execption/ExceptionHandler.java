package com.gaaji.chat.execption;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(AbstractApiException.class)
    public ResponseEntity<?> handleApiException(AbstractApiException e, HttpServletRequest request) {
        return ResponseEntity.status(e.getHttpStatus()).body(ErrorResponse.createErrorResponse(e, request.getRequestURI()));
    }
}
