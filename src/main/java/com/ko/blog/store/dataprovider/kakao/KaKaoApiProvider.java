package com.ko.blog.store.dataprovider.kakao;

import com.ko.blog.store.webapi.kakaoApiRepository.payload.SearchBlogPayload;

public interface KaKaoApiProvider {
    SearchBlogPayload getSearchBlog(
            final String query, final String sort, final String page, final String size);
}
