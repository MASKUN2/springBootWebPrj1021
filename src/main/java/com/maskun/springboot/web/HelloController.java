package com.maskun.springboot.web;

import com.maskun.springboot.web.dto.HelloResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(Authentication authentication, Model model) {
        model.addAttribute("authentication",authentication);
        log.debug(authentication.toString());
        return authentication.getName();
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name")String name, @RequestParam("amount")int amount){

        return new HelloResponseDto(name, amount);

    }
}


