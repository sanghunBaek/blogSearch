package com.ko.blog.service.common.exception;

import com.ko.blog.service.common.response.ResponseError;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvisor {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity exceptionHandler(MissingServletRequestParameterException e) {
        String errorUuid = generateErrorUuid();
        ResponseError responseError = new ResponseError();
        responseError.setDebug(e.toString());
        responseError.setMessage(e.getMessage());
        responseError.setErrorUUID(errorUuid);
        log.info("Error Message : {}, Error UUID : {} ", e.getMessage(), errorUuid);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseError.builder().debug(e.toString()).message(e.getMessage()).build());
    }

    private String generateErrorUuid() {
        return UUID.randomUUID().toString();
    }
}
