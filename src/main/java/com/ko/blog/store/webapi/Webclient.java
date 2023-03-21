package com.ko.blog.store.webapi;

import com.ko.blog.service.common.config.ApiKey;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Service
@Slf4j
public class Webclient {
    private final ApiKey API_KEY;

    public Webclient(ApiKey apiKey) {
        this.API_KEY = apiKey;
    }

    HttpClient httpClient =
            HttpClient.create()
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                    .responseTimeout(Duration.ofMillis(5000))
                    .doOnConnected(
                            conn ->
                                    conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                                            .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

    public WebClient kakaoApi() {
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient.wiretap("kakaoApi")))
                .baseUrl(API_KEY.getKakaoApiUrl())
                .defaultHeader("Authorization", "KakaoAK " + API_KEY.getKakaoApiKey())
                .filter(
                        (request, next) -> {
                            log.info("Request: {} {}", request.method(), request.url());
                            ClientResponse response = next.exchange(request).block();
                            log.info(
                                    "Response: {} {}", response.statusCode(), response.headers().asHttpHeaders());
                            return Mono.just(response);
                        })
                .build();
    }

    public WebClient naverApi() {
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient.wiretap("naverApi")))
                .baseUrl(API_KEY.getNaverApiUrl())
                .defaultHeader("X-Naver-Client-Id", API_KEY.getNaverApiClientId())
                .defaultHeader("X-Naver-Client-Secret", API_KEY.getNaverApiClientSecret())
                .filter(
                        (request, next) -> {
                            log.info("Request: {} {}", request.method(), request.url());
                            ClientResponse response = next.exchange(request).block();
                            log.info(
                                    "Response: {} {}", response.statusCode(), response.headers().asHttpHeaders());
                            return Mono.just(response);
                        })
                .build();
    }
}
