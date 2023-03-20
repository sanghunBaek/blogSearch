package com.ko.blog.store.dataprovider.serch;

import com.ko.blog.store.model.entity.SearchHistoryEntity;

public interface SearchHistoryEntityProvider {
    SearchHistoryEntity saveSearchHistory(final String keyword);
}
