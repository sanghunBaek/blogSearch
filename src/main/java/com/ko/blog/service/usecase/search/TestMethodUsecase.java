package com.ko.blog.service.usecase.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestMethodUsecase {

    public Result execute() {
        return new Result();
    }

    @Data
    @JsonIgnoreProperties
    public static class Command implements Serializable {
        private static final long serialVersionUID = -1678398930299214134L;
        private String name;
        private String word;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result implements Serializable {}
}
