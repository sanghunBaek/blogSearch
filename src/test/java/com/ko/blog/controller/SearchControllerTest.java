package com.ko.blog.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ko.blog.config.RestDocsConfiguration;
import com.ko.blog.service.controller.SearchController;
import com.ko.blog.service.usecase.search.BlogSearchUsecase;
import com.ko.blog.service.usecase.search.SearchRankUsecase;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
@WebMvcTest(SearchController.class)
@ExtendWith(RestDocumentationExtension.class)
public class SearchControllerTest {
    @Autowired private MockMvc mockMvc;

    @Autowired protected RestDocumentationResultHandler restDocs;
    @MockBean // MemberController이 의존하는 빈을 모킹
    private SearchRankUsecase searchRankUsecase;

    @MockBean // MemberController이 의존하는 빈을 모킹
    private BlogSearchUsecase blogSearchUsecase;

    @BeforeEach
    void setUp(final WebApplicationContext context, final RestDocumentationContextProvider provider) {
        this.mockMvc =
                MockMvcBuilders.webAppContextSetup(context)
                        .apply(MockMvcRestDocumentation.documentationConfiguration(provider)) // rest docs 설정 주입
                        .alwaysDo(MockMvcResultHandlers.print()) // andDo(print()) 코드 포함
                        .alwaysDo(restDocs) // pretty 패턴과 문서 디렉토리 명 정해준것 적용
                        .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 한글 깨짐 방지
                        .build();
    }

    @DisplayName("블로그 검색 결과 출력")
    @Test
    void searchBlog() throws Exception {
        BlogSearchUsecase.Result.Document document = new BlogSearchUsecase.Result.Document();
        document.setBlogname("정란수의 브런치");
        document.setUrl("https://brunch.co.kr/@tourism/91");
        document.setContents(
                "이 점은 <b>집</b>을 지으면서 고민해보아야 한다. 하지만, 금액에 대한 가성비 대비 크게 문제되지 않을 부분이라 생각하여 설계로 극복하자고 생각했다. 전체 <b>집</b><b>짓기</b>의 기본방향은 크게 세 가지이다. 우선은 여가의 영역 증대이다. 현대 시대 일도 중요하지만, 여가시간 <b>집</b>에서 어떻게 보내느냐가 중요하니깐 이를 기본적...");
        document.setTitle("작은 <b>집</b> <b>짓기</b> 기본컨셉 - <b>집</b><b>짓기</b> 초기구상하기");
        document.setThumbnail("http://search3.kakaocdn.net/argon/130x130_85_c/7r6ygzbvBDc");
        document.setDatetime("2017-05-07T18:50:07.000+09:00");

        BlogSearchUsecase.Result.MetaData meta = new BlogSearchUsecase.Result.MetaData();
        meta.setPageableCount(1);
        meta.setTotalCount(3);

        BlogSearchUsecase.Result result = new BlogSearchUsecase.Result();
        result.setDocuments(List.of(document));
        result.setMeta(meta);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("query", "검색");
        params.add("sort", "accuracy");
        params.add("page", "2");
        params.add("size", "3");

        when(blogSearchUsecase.execute(any())).thenReturn(result);
        mockMvc
                .perform(get("/v1/search/blog").params(params))
                .andDo(
                        restDocs.document(
                                requestParameters(
                                        parameterWithName("query")
                                                .description(
                                                        "검색을 원하는 질의어, 특정 블로그 글만 검색하고 싶은 경우 블로그 url과 검색어를 공백 구분자로 넣을 수 있다."),
                                        parameterWithName("sort")
                                                .description("결과문서 정렬방식으로 accuracy(정확도순), recency(최신순), 기본값 accuracy"),
                                        parameterWithName("page").description("결과 페이지번호 1~50 사이의 값, 기본값 1"),
                                        parameterWithName("size").description("한 페이지에 보여질 문서수 1~50 사이의 값, 기본값 10")),
                                responseFields(
                                        subsectionWithPath("documents").description("문서"),
                                        fieldWithPath("documents.[].title").description("블로그 글 제목"),
                                        fieldWithPath("documents.[].contents").description("블로그 글 요약"),
                                        fieldWithPath("documents.[].url").description("블로그 글 url"),
                                        fieldWithPath("documents.[].blogname").description("블로그 이름"),
                                        fieldWithPath("documents.[].thumbnail")
                                                .description("검색 시스템에서 추찰한 대표 미리보기 이미지 url"),
                                        fieldWithPath("documents.[].datetime").description("블로그 글 작성시간, ISO 8601 "),
                                        subsectionWithPath("meta").description("메타정보"),
                                        fieldWithPath("meta.totalCount").description("검색된 문서 수"),
                                        fieldWithPath("meta.isEnd").description("다음 페이지 존재 여부"),
                                        fieldWithPath("meta.pageableCount").description("검색된 문서 수중 노출 가능한 문서 수"))))
                .andExpect(status().isOk());
    }

    @DisplayName("검색 TOP 10 랭킹 정보를 가져온다.")
    @Test
    void searchTop10Rank() throws Exception {
        SearchRankUsecase.Result.Rank rank = new SearchRankUsecase.Result.Rank();
        rank.setCount(2);
        rank.setKeyword("예금");

        SearchRankUsecase.Result result =
                SearchRankUsecase.Result.builder().ranks(List.of(rank)).build();

        when(searchRankUsecase.execute()).thenReturn(result);
        mockMvc
                .perform(get("/v1/search/rank"))
                .andDo(
                        restDocs.document(
                                responseFields(
                                        subsectionWithPath("ranks").description("키워드 랭킹"),
                                        fieldWithPath("ranks.[].keyword").description("키워드"),
                                        fieldWithPath("ranks.[].count").description("카운트"))))
                .andExpect(status().isOk());
    }
}
