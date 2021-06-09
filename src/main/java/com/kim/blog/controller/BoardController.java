package com.kim.blog.controller;

import com.kim.blog.config.auth.PrincipalDetail;
import com.kim.blog.service.BoardService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

// 파라미터로 세션에 접근한다. @AuthenticationPrincipal PrincipalDetail principal

    @GetMapping({"", "/"})
    public String index(Model model) {
        model.addAttribute("boards", boardService.list());
        return "index";
    }

    // 글쓰기 저장하기 - USER 권한이 필요하다.
    @GetMapping("/board/saveForm")
    public String saveForm(){
        return "board/saveForm";
    }


    //  글 상세보기
    @GetMapping("/board/{id}")
    public String findById(@PathVariable int id, Model model) {
        model.addAttribute("board", boardService.detailView(id));
        return "board/detail";
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id, Model model){
        model.addAttribute("board", boardService.detailView(id));
        return "board/updateForm";
    }
}
