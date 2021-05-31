package com.kim.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 이 주소로 허용한다.
// 그냥 주소가 / 이면 index.jsp 허용한다.
// static 이하에 있는 css, js, image 를 허용한다.

@Controller
public class UserController {

    @GetMapping("/auth/joinForm")
    public String joinForm() {
        System.out.println("이 부분이 회원가입 1경로");
        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm(){
        System.out.println("로그인폼 부분이다.");
        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm(){
        return "user/updateForm";
    }
}
