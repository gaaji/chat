package com.gaaji.chat.execption;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler(AbstractApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(AbstractApiException e, HttpServletRequest request) {
        return ResponseEntity.status(e.getHttpStatus()).body(ErrorResponse.createErrorResponse(e, request.getRequestURI()));
    }
}
