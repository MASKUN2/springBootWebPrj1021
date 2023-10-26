package com.maskun.springboot.web;

import com.maskun.springboot.domain.posts.Posts;
import com.maskun.springboot.domain.posts.PostsRepository;
import com.maskun.springboot.service.posts.PostsService;
import com.maskun.springboot.web.dto.PostsSaveRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexControllerTest {
    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    PostsService postsService;

    @Test
    public void index페이지가열린다() {
        // restTemplate.getForObject 는 리턴타입을 지정한 것으로 받는다. getForEntity는 는 ResponseEntity<String> responseEntity 로 받을 수 있고 http요청의 상세 정보를 확인할 때 사용한다. 이는 단순한 페이지 요청이므로 getForObject 사용
        //when
        String text = restTemplate.getForObject("/", String.class);

        //then
        System.out.println(text); // HTML문서 전체가 옴 (태그포함) 그래서 isEqual이 아니라 contains를 쓴다.
        assertThat(text).contains("스프링 부트로 시작하는 웹 서비스");


    }

    @Test
    public void postsList_조회된다 () {
        //given
        //서비스 단에서 저장한 내용을 인덱스 화면에서 잘 보여주는지 체크하기
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder().title("제목테스트").content("내용테스트").author("작성자테스트").build();
        postsService.save(requestDto);

        //when
        String text = restTemplate.getForObject("/", String.class);

        //then
        assertThat(text).contains("제목테스트").contains("작성자테스트"); //.contains("내용테스트")는 리스트 화면에서 출력하지 않고 상세보기로 접근가능
    }
}