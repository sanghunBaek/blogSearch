package com.ko.blog.store.model.repository;

import com.ko.blog.store.model.entity.SearchHistoryEntity;
import java.util.List;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistoryEntity, String> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<SearchHistoryEntity> findByKeyword(String keyword);

    List<SearchHistoryEntity> findTop10ByOrderByCountDesc();
}
