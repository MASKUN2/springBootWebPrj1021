package com.maskun.springboot.web;

import com.maskun.springboot.service.posts.PostsService;
import com.maskun.springboot.web.dto.PostsListResponseDto;
import com.maskun.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model) { // 모델객체는 매핑된 메소드의 파라미터에 선언하면 알아서 스프링MVC가 만들어서 response준다.
        List<PostsListResponseDto> postsList = postsService.findAllDesc();
        model.addAttribute("posts", postsList); // 키 : 밸류를 둘다 받아주는 것
        return "index"; // 머스타치가 앞뒤 경로와 확장자를 모두 자동제공한다.
    }
    @GetMapping("/posts/update/{id}")
    public String postsUpdate(Model model, @PathVariable Long id) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }
}
