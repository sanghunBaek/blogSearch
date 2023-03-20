package com.ko.blog.store.model.repository;

import com.ko.blog.store.model.entity.SearchHistoryEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistoryEntity, String> {
    Optional<SearchHistoryEntity> findByKeyword(String keyword);

    List<SearchHistoryEntity> findTop10ByOrderByCountDesc();
}
