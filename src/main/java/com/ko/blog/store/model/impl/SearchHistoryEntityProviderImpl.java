package com.ko.blog.store.model.impl;

import com.ko.blog.store.dataprovider.serch.SearchHistoryEntityProvider;
import com.ko.blog.store.model.entity.SearchHistoryEntity;
import com.ko.blog.store.model.repository.SearchHistoryRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SearchHistoryEntityProviderImpl implements SearchHistoryEntityProvider {

    private final SearchHistoryRepository searchHistoryRepository;

    public SearchHistoryEntity saveSearchHistory(final String keyword) {
        Optional<SearchHistoryEntity> entity = searchHistoryRepository.findByKeyword(keyword);

        if (entity.isEmpty()) {
            SearchHistoryEntity searchHistory =
                    SearchHistoryEntity.builder().keyword(keyword).count(1).build();
            return searchHistoryRepository.save(searchHistory);
        }

        SearchHistoryEntity searchHistory = entity.get();
        searchHistory.setCount(searchHistory.getCount() + 1);
        return searchHistoryRepository.save(searchHistory);
    }
}
