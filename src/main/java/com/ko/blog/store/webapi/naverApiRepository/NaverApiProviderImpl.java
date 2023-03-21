package com.ko.blog.store.webapi.naverApiRepository;

import com.ko.blog.store.dataprovider.naver.NaverApiProvider;
import com.ko.blog.store.webapi.Webclient;
import com.ko.blog.store.webapi.naverApiRepository.payload.SearchPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
@RequiredArgsConstructor
public class NaverApiProviderImpl implements NaverApiProvider {

    private final Webclient webclient;

    public SearchPayload getSearch(
            final String query, final String sort, final String page, final String size) {

        String naverSort = sort.equals("recency") ? "date" : "sim";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("query", query);
        params.add("sort", naverSort);
        params.add("start", page);
        params.add("display", size);

        SearchPayload payloadMono =
                webclient
                        .naverApi()
                        .get()
                        .uri(uriBuilder -> uriBuilder.path("/v1/search/blog.json").queryParams(params).build())
                        .retrieve()
                        .bodyToMono(SearchPayload.class)
                        .block();

        return payloadMono;
    }
}
