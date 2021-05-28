package com.kim.blog.controller;

import com.kim.blog.config.auth.PrincipalDetail;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @GetMapping({"", "/"})
    public String index(@AuthenticationPrincipal PrincipalDetail principal){ // 파라미터로 세션에 접근한다.
        System.out.println("이 부분 실행되는건가 " + principal.getUsername());
        return "index";
    }
}
