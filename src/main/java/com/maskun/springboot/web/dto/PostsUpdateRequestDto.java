package com.maskun.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsUpdateRequestDto {
    private String title; // 수정에 국한하기 때문에 PostSaveRequestDto와 다르게 author 필드가 없음(작성자가 수정하기 때문)
    private String content;

    @Builder
    public PostsUpdateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
