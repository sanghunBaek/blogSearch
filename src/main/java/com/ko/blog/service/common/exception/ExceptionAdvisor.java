package com.ko.blog.service.common.exception;

import com.ko.blog.service.common.response.ResponseError;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvisor {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(code=HttpStatus.BAD_REQUEST)
    public ResponseEntity parameterExceptionHandler(MissingServletRequestParameterException e) {
        String errorUuid = generateErrorUuid();
        ResponseError responseError =
                ResponseError.builder()
                        .debug(e.toString())
                        .code(ErrorCode.PARAMETER_ERROR.getCode())
                        .message(ErrorCode.PARAMETER_ERROR.getMessage())
                        .errorUUID(errorUuid)
                        .build();
        log.info("Error Message : {}, Error UUID : {} ", e.getMessage(), errorUuid);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseError);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code=HttpStatus.BAD_REQUEST)
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ResponseEntity exceptionHandler(Exception e) {
        String errorUuid = generateErrorUuid();
        ResponseError responseError =
            ResponseError.builder()
                .debug(e.toString())
                .code(ErrorCode.SYSTEM_ERROR.getCode())
                .message(ErrorCode.SYSTEM_ERROR.getMessage())
                .errorUUID(errorUuid)
                .build();
        log.info("Error Message : {}, Error UUID : {} ", e.getMessage(), errorUuid);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseError);
    }

    private String generateErrorUuid() {
        return UUID.randomUUID().toString();
    }
}
