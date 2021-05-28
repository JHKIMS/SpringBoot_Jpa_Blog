package com.kim.blog.controller;

import com.kim.blog.config.auth.PrincipalDetail;
import com.kim.blog.service.BoardService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping({"", "/"})
    public String index(Model model) { // 파라미터로 세션에 접근한다. @AuthenticationPrincipal PrincipalDetail principal
        model.addAttribute("boards", boardService.list());
        return "index";
    }

    // 글쓰기 저장하기 - USER 권한이 필요하다.
    @GetMapping("/board/saveForm")
    public String saveForm(){
        return "board/saveForm";
    }
}
