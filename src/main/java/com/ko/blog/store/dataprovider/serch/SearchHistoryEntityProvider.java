package com.ko.blog.store.dataprovider.serch;

import com.ko.blog.store.model.entity.SearchHistoryEntity;
import java.util.List;

public interface SearchHistoryEntityProvider {
    SearchHistoryEntity saveSearchHistory(final String keyword);

    List<SearchHistoryEntity> getKeywordTopTen();
}
