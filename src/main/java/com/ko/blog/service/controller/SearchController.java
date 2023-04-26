package com.ko.blog.service.controller;

import com.ko.blog.service.usecase.search.BlogSearchUsecase;
import com.ko.blog.service.usecase.search.SearchRankUsecase;
import com.ko.blog.service.usecase.search.TestMethodUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class SearchController {

    private final BlogSearchUsecase blogSearchUsecase;
    private final SearchRankUsecase searchRankUsecase;
    private final TestMethodUsecase testMethodUsecase;

    @GetMapping("/search/blog")
    public ResponseEntity<?> searchBlog(
            @RequestParam String query,
            @RequestParam(required = false, name = "sort", defaultValue = "") String sort,
            @RequestParam(required = false, name = "page", defaultValue = "") String page,
            @RequestParam(required = false, name = "size", defaultValue = "") String size) {
        BlogSearchUsecase.Command command =
                BlogSearchUsecase.Command.builder().query(query).sort(sort).page(page).size(size).build();
        return ResponseEntity.ok().body(blogSearchUsecase.execute(command));
    }

    @GetMapping("/search/rank")
    public ResponseEntity<?> searchRank() {
        return ResponseEntity.ok().body(searchRankUsecase.execute());
    }

    @PostMapping("/search/test")
    public ResponseEntity<?> testPostMethod(@RequestBody TestMethodUsecase.Command command) {
        String test = "command";
        return ResponseEntity.ok().body("test");
    }
}
