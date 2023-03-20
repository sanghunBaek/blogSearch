package com.ko.blog.store.model.impl;

import com.ko.blog.store.dataprovider.serch.SearchHistoryEntityProvider;
import com.ko.blog.store.model.repository.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class SearchHistoryEntityProviderImpl implements SearchHistoryEntityProvider {

  final private SearchHistoryRepository searchHistoryRepository;

  public boolean checkKewordExist(final String keyword){
    return searchHistoryRepository.existsByKeyword(keyword);
  }

}
