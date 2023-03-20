package com.ko.blog.service.common.exception;

public enum ErrorCode {
    PARAMETER_ERROR("1", "필수 파라미터 입력이 필요합니다."),
    SYSTEM_ERROR("-1", "알 수 없는 애러가 발생했습니다.");

    private String code;
    private String message;

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    ErrorCode(String string, String errorMessage) {
        this.code = string;
        this.message = errorMessage;
    }
}
