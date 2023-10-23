package com.maskun.springboot.domain.posts;

import javafx.beans.binding.When;
import org.hamcrest.CoreMatchers;
import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기(){
        //given
        String title = "테스트 글제목";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder().title(title).content(content).author("maskunflower2@naver.com").build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        Assert.assertThat(posts.getTitle(), CoreMatchers.is(title));
        Assert.assertThat(posts.getContent(), CoreMatchers.is(content));
    }

    @Test
    // 굳이 restTemplate으로 테스트 하지 않는 이유는 web레이어가 아닌 서비스 레이어 아래로만 검증해도 무방하기 때문이다.
    public void BaseTimeEntity_등록(){
        // 현재시간으로 now 객체를 만듬
        LocalDateTime now = LocalDateTime.now();

        Long id = postsRepository.save(Posts.builder().title("t").content("c").author("a").build()).getId();

        Posts posts = postsRepository.findById(id).get();

        assertThat(posts.getCreateDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);



    }

}