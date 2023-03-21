package com.ko.blog.service.usecase.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ko.blog.store.dataprovider.kakao.KaKaoApiProvider;
import com.ko.blog.store.dataprovider.naver.NaverApiProvider;
import com.ko.blog.store.dataprovider.serch.SearchHistoryEntityProvider;
import com.ko.blog.store.webapi.kakaoApiRepository.payload.SearchBlogPayload;
import com.ko.blog.store.webapi.naverApiRepository.payload.SearchPayload;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class BlogSearchUsecase {

    private final KaKaoApiProvider searchDataApiProvider;
    private final NaverApiProvider naverApiProvider;

    private final SearchHistoryEntityProvider searchHistoryEntityProvider;
    private final ModelMapper modelMapper;

    public Result execute(Command command) {
        SearchBlogPayload searchBlogPayload =
                searchDataApiProvider.getSearchBlog(
                        command.getQuery(), command.getSort(), command.getPage(), command.getSize());

        String query = command.getQuery();
        List<String> queryList = List.of(query.split(" "));
        String keyword = queryList.size() == 1 ? queryList.get(0) : queryList.get(1);

        // 정상적으로 조회가 된 경우
        if (!StringUtils.hasLength(searchBlogPayload.getErrorType())) {
            searchHistoryEntityProvider.saveSearchHistory(keyword);
            return modelMapper.map(searchBlogPayload, Result.class);
        }
        // 카카오 검색 api 오류가 있는 경우
        SearchPayload searchPayload =
                naverApiProvider.getSearch(
                        keyword, command.getSort(), command.getPage(), command.getSize());
        searchHistoryEntityProvider.saveSearchHistory(keyword);
        return modelMapper.map(searchPayload, Result.class);
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Command implements Serializable {
        private static final long serialVersionUID = 7271478134600677705L;
        private String query;
        private String sort;
        private String page;
        private String size;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result implements Serializable {
        private static final long serialVersionUID = -8294362765153422184L;
        private MetaData meta;
        private List<Document> documents;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class MetaData implements Serializable {

            private static final long serialVersionUID = -6144764557691496711L;
            private Integer totalCount;
            private Integer pageableCount;
            private Boolean isEnd;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Document implements Serializable {

            private static final long serialVersionUID = -6658542476018299797L;
            private String title;
            private String contents;
            private String url;
            private String blogname;
            private String thumbnail;
            private String datetime;
        }
    }
}
