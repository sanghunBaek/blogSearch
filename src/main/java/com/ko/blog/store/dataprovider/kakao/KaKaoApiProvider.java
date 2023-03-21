package com.ko.blog.store.dataprovider.kakao;

import com.ko.blog.store.webapi.kakaoApiRepository.payload.SearchBlogPayload;
import org.springframework.cache.annotation.Cacheable;

public interface KaKaoApiProvider {
    @Cacheable(cacheNames = "blogSearch")
    SearchBlogPayload getSearchBlog(
            final String query, final String sort, final String page, final String size);
}
