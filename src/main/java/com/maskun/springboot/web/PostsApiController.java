package com.maskun.springboot.web;

import com.maskun.springboot.service.posts.PostsService;
import com.maskun.springboot.web.dto.PostsSaveRequestDto;
import com.maskun.springboot.web.dto.PostsUpdateRequestDto;
import com.maskun.springboot.web.dto.postsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

//클래스와 가까울 수록 중요한 어노테이션
//@RequiredArgsConstructor 는 기본적으로 인수없는 기본 생성자를 Private으로 생성한다.
@RequiredArgsConstructor
@RestController
//게시글(posts)의 REST API컨트롤러
public class PostsApiController {

    //스프링은, 클래스가 @Component 이고 생성자가 단 하나라면 그 생성자에 DI를 자동으로 수행한다. 따라서 @RequiredArgsConstructor으로 생성자에 포함되는 final 필드인 PostsService postsService는 알아서 DI된다.
    private final PostsService postsService;

    //@PostMapping("/api/v1/posts") 은 Post 요청을 해당 Url경로로 받았을 때 작동하는 메소드를 말함
    @PostMapping("/api/v1/posts")

        //@RequestBody는 RequestMappingHandlerAdapter(데이터 형식에 따른 메시지 변환기 HttpMessageConverter를 가짐)를 통해 다양한 요청 데이터 형식을 받아 자바 오브젝트로 만들어준다. 아마도 요청 데이터 수가 부족한 경우 해당 필드를 Null로 만드는 듯하다.
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        //컨트롤러는 데이터를 받아 서비스 메소드에게 보낸다. 서비스는 트랜젝션을 관리하고 실제 비지니스로직은 도메인에서 실시한다.
        return postsService.save(requestDto);
    }
    //풋 매핑은 만약 객체가 이미 존재한다면 내용을 전체적으로 업데이트함. 일반적으로 PUT은 해당 객체가 없을 경우 새로 생성하지만 비지니스로직에서 이를 자유롭게 지정가능함
    @PutMapping("/api/v1/posts/{id}")
    //@PathVariable Long id 는 URL상의 변수를 쓰겠다는 의미
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id,requestDto);
    }
    //조회 겟 요청
    @GetMapping("/api/v1/posts/{id}")
    public postsResponseDto findById (@PathVariable Long id) {
        return postsService.findById(id);
    }
}
