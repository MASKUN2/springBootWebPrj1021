package com.maskun.springboot.web;

import com.maskun.springboot.domain.posts.Posts;
import com.maskun.springboot.domain.posts.PostsRepository;
import com.maskun.springboot.web.dto.PostsSaveRequestDto;
import com.maskun.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat; //굳이 스테틱을 사용하는 이유는 테스트 메소드가 바뀔 때마다 assertThat 객체를 새로 생성하지 않고 코드량을 줄이기 위해서다.

@RunWith(SpringRunner.class) // 스프링 프레임워크 환경에서 테스트하겠다는 의미이며 필수다.

//스프링부트환경에서 테스트하겠다는 것이다. ebEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT는 웹 환경, 랜덤포트를 사용하여 실제 가동중인 서버 포트와 충돌되지 않게 하는 등 안정성을 보장한다. TestRestTemplate 가 해당 설정을 의존한다.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort // 포트번호를 받을 변수
    private int port;

    @Autowired
    private TestRestTemplate restTemplate; // REST 테스팅을 위한 스프링 클래스. @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) 하지 않은 경우 스프링은 IoC에 빈을 생성하지 않아 주입되지 않는다.

    @Autowired
    private PostsRepository postsRepository; // 테스트할 레파지토리 인터페이스 구현체

    @After //테스트가 끝나고 레파지토리에 영향을 모두 CleanUp 하겠다는 것
    public  void teardown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test//실제 테스트할 메소드에 붙인다. 보통 식별이 쉽도록 한글을 섞는다.
    public  void Posts_등록된다() throws Exception{
        //given
        String title = "title"; // 게시글의 제목
        String content = "content"; // 내용

        //웹환경의 실제 구동 테스트를 위해서 테스트용 Dto를 작성
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title).content(content).author("author").build();

        //앞서 랜덤포트 변수를 활용하여 request url작성
        String url = "http://localhost:" + port + "/api/v1/posts";

        //when
        // restTemplate.postForEntity는 Post 방식으로 자바 오브젝트(dto)를 자동으로 알맞은 웹 데이터형식으로 변환하여 http post 형식으로 요청하는 테스트 메소드다. 마지막 pram은 응답의 클래스 타입을 정하는 것.ResponseEntity<Long> 로 응답을 받는다.
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        //assertThat은 assertj 패키지이며 junit과 비슷하다. assertThat(값1).isEqualto(값2)는 두 값의 일치여부를 비교하겠다는 것이다.isGreaterThan은 값이 이것보다 큰지 확인한다.
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    public void Posts_수정된다()throws Exception{
        //given
        Posts savedPosts = postsRepository.save(Posts.builder().title("title").content("content").author("author").build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder().title(expectedTitle).content(expectedContent).build();

        String url = "http://localhost:"+port+"/api/v1/posts/"+updateId;
        //httpEntity는 자바 오브젝트를 가지고 헤더 바디 등을 직접 수정가능하게 한다.
        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        // restTemplate.exchange 는 다양한 요청(get,post,put...)등을 직접 지정해서 요청할 수 있는 메소드 여기선 HttpMethod.PUT 메소드를 인자로 지정할 수 있다. 이 경우 httpEntity 객체가 필요하다.
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }
}