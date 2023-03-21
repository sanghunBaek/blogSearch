package com.ko.blog.store.dataprovider.serch;

import com.ko.blog.store.model.entity.SearchHistoryEntity;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;

public interface SearchHistoryEntityProvider {
    SearchHistoryEntity saveSearchHistory(final String keyword);

    @Cacheable(cacheNames = "searchRank")
    List<SearchHistoryEntity> getKeywordTopTen();
}
