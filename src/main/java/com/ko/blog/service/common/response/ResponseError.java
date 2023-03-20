package com.ko.blog.service.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseError {
    private String message;
    private String code;
    private String debug;
    private String errorUUID;
}
