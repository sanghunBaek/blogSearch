package com.ko.blog.service.common.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CacheType {
    BLOG_SEARCH("blogSearch", 10, 2000),
    SEARCH_RANK("searchRank", 30, 2000);

    private final String cacheName;
    private final int expiredAfterWrite;
    private final int maximumSize;
}
