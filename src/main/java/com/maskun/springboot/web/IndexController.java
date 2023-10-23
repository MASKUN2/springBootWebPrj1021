package com.maskun.springboot.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index() {
        return "index"; // 머스타치가 앞뒤 경로와 확장자를 모두 자동제공한다.
    }
}
