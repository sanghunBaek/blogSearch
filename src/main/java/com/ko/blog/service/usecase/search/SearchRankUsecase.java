package com.ko.blog.service.usecase.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ko.blog.service.usecase.search.SearchRankUsecase.Result.Rank;
import com.ko.blog.store.dataprovider.serch.SearchHistoryEntityProvider;
import com.ko.blog.store.model.entity.SearchHistoryEntity;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchRankUsecase {

    private final SearchHistoryEntityProvider searchHistoryEntityProvider;
    private final ModelMapper modelMapper;

    public Result execute() {
        List<SearchHistoryEntity> entities = searchHistoryEntityProvider.getKeywordTopTen();
        List<Rank> ranks =
                entities.stream()
                        .map(entity -> modelMapper.map(entity, Result.Rank.class))
                        .collect(Collectors.toList());

        return Result.builder().ranks(ranks).build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result implements Serializable {

        private static final long serialVersionUID = -1540525479647566358L;
        private List<Rank> ranks;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Rank implements Serializable {
            private static final long serialVersionUID = 617662108097876486L;
            private String keyword;
            private Integer count;
        }
    }
}
