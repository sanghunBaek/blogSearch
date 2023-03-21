package com.ko.blog.store.webapi.kakaoApiRepository;

import com.ko.blog.store.dataprovider.kakao.KaKaoApiProvider;
import com.ko.blog.store.webapi.Webclient;
import com.ko.blog.store.webapi.kakaoApiRepository.payload.SearchBlogPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoApiProviderImpl implements KaKaoApiProvider {

    private final Webclient webclient;

    public SearchBlogPayload getSearchBlog(
            final String query, final String sort, final String page, final String size) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("query", query);
        params.add("sort", sort);
        params.add("page", page);
        params.add("size", size);

        SearchBlogPayload payloadMono =
                webclient
                        .kakaoApi()
                        .get()
                        .uri(uriBuilder -> uriBuilder.path("/v2/search/blog").queryParams(params).build())
                        .exchangeToMono(
                                clientResponse -> {
                                    if (!clientResponse.statusCode().equals(HttpStatus.OK)
                                            || !clientResponse.statusCode().equals(HttpStatus.CREATED)) {
                                        log.error(
                                                "카카오 블로그 검색 api error 발생 :{},{}",
                                                clientResponse.rawStatusCode(),
                                                clientResponse.bodyToMono(String.class));
                                    }
                                    return clientResponse.bodyToMono(SearchBlogPayload.class);
                                })
                        .block();

        return payloadMono;
    }
}
