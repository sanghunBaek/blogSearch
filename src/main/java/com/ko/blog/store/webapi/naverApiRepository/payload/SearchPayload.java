package com.ko.blog.store.webapi.naverApiRepository.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
public class SearchPayload {
    @JsonProperty("items")
    private List<Document> documents;
    @Data
    public static class Document {
        private String title;

        @JsonProperty("description")
        private String contents;

        @JsonProperty("link")
        private String url;

        @JsonProperty("bloggername")
        private String blogname;

        @JsonProperty("postdate")
        private String datetime;
    }
}
