package com.ko.blog.service.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiKey {
    @Value("${extra-key.kakao.url}")
    private String kakaoApiUrl;

    @Value("${extra-key.kakao.api-key}")
    private String kakaoApiKey;

    @Value("${extra-key.naver.url}")
    private String naverApiUrl;

    @Value("${extra-key.naver.client-id}")
    private String naverApiClientId;

    @Value("${extra-key.naver.client-secret}")
    private String naverApiClientSecret;

    public String getKakaoApiKey() {
        return kakaoApiKey;
    }

    public String getKakaoApiUrl() {
        return kakaoApiUrl;
    }

    public String getNaverApiUrl() {
        return naverApiUrl;
    }

    public String getNaverApiClientId() {
        return naverApiClientId;
    }

    public String getNaverApiClientSecret() {
        return naverApiClientSecret;
    }
}
