package com.ko.blog.service.usecase.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ko.blog.store.dataprovider.kakao.SearchDataApiProvider;
import com.ko.blog.store.webapi.kakaoApiRepository.payload.SearchBlogPayload;
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

@Service
@RequiredArgsConstructor
public class BlogSearchUsecase {

    private final SearchDataApiProvider searchDataApiProvider;
    private final ModelMapper modelMapper;

    public Result execute(Command command) {
        SearchBlogPayload searchBlogPayload =
                searchDataApiProvider.getSearchBlog(
                        command.getQuery(), command.getSort(), command.getPage(), command.getSize());
        return modelMapper.map(searchBlogPayload, Result.class);
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
            private String datatime;
        }
    }
}
