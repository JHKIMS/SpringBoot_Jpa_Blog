package com.kim.blog.controller.api;

import com.kim.blog.config.auth.PrincipalDetail;
import com.kim.blog.dto.ReplySaveReqDto;
import com.kim.blog.dto.ResponseDto;
import com.kim.blog.model.Board;
import com.kim.blog.model.Reply;
import com.kim.blog.model.User;
import com.kim.blog.repository.ReplyRepository;
import com.kim.blog.service.BoardService;
import com.kim.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
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
        boardService.deleteByBoard(id);
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }

    // 글 수정
    @PutMapping("/api/board/{id}")
    public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board) {
        boardService.updateBoard(id, board);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    // 댓글 저장  :: 데이터를 받는 경우에는 DTO를 만들어서 받아서 처리하는 것이 좋다.
    @PostMapping("/api/board/{boardId}/reply")
    public ResponseDto<Integer> replySave(@RequestBody ReplySaveReqDto replySaveReqDto){
        boardService.replyWrite(replySaveReqDto); // 보드 객체와, 세션의 아이디를 가져온다.
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/api/board/{boardId}/reply/{replyId}")
    public ResponseDto<Integer> replyDelete(@PathVariable int replyId){
        boardService.deleteReply(replyId);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }



}
