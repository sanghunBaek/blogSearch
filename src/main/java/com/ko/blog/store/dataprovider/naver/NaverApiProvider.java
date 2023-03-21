package com.ko.blog.store.dataprovider.naver;

import com.ko.blog.store.webapi.naverApiRepository.payload.SearchPayload;

public interface NaverApiProvider {
    SearchPayload getSearch(
            final String query, final String sort, final String page, final String size);
}
