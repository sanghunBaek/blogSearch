package com.ko.blog.store.model.repository;

import com.ko.blog.store.model.entity.SearchHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistoryEntity,String> {

  boolean existsByKeyword(String keyword);
}
