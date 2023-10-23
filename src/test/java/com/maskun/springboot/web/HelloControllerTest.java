package com.maskun.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {
    @Autowired
    // MockMvc란 내가 컨트롤러 테스트를 하고싶을 때 실제 서버에 구현한 애플리케이션을 올리지 않고(실제 서블릿 컨테이너를 사용하지 않고) 테스트용으로 시뮬레이션하여 MVC가 되도록 도와주는 클래스다!
    //TestRestTemplate은 웹 클라이언트 관점이고 MockMvc는 서비스레이어 관점이다.
    private MockMvc mvc;

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                        .param("name", name)
                        .param("amount", String.valueOf(amount)))
                             .andExpect(status().isOk())
                                .andExpect(jsonPath("$.name", is(name)))
                                .andExpect(jsonPath("$.amount", is(amount)));
    }
}
