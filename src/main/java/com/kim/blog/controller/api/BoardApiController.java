package com.kim.blog.controller.api;

import com.kim.blog.config.auth.PrincipalDetail;
import com.kim.blog.dto.ResponseDto;
import com.kim.blog.model.Board;
import com.kim.blog.model.User;
import com.kim.blog.service.BoardService;
import com.kim.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    // 글 저장 : 게시글 작성
    @PostMapping("/api/board")
    public ResponseDto<Integer> save(@RequestBody Board board,
                                     @AuthenticationPrincipal PrincipalDetail principal){
        boardService.write(board, principal.getUser()); // 보드 객체와, 세션의 아이디를 가져온다.
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }

    // 삭제하기
    @DeleteMapping("/api/board/{id}")
    public ResponseDto<Integer> deleteById(@PathVariable int id){
        System.out.println("여기 1");
        boardService.deleteByBoard(id);
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }

    // 글 수정
    @PutMapping("/api/board/{id}")
    public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board) {
        boardService.updateBoard(id, board);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}
