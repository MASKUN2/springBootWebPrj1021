package com.maskun.springboot.web.dto;

import com.maskun.springboot.domain.posts.Posts;
import lombok.Getter;

@Getter
//게시글 조회 DTO
public class PostsResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;

    //엔티티를 인자로 받아 Dto생성자로 필드 초기화
    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}
