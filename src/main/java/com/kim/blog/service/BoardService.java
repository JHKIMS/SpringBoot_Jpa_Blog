package com.kim.blog.service;

import com.kim.blog.dto.ReplySaveReqDto;
import com.kim.blog.model.Board;
import com.kim.blog.model.Reply;
import com.kim.blog.model.User;
import com.kim.blog.repository.BoardRepository;
import com.kim.blog.repository.ReplyRepository;
import com.kim.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;

    @Transactional // 게시글 작성
    public void write(Board board, User user) {
        board.setCount(0); // 조회수
        board.setUser(user);
        boardRepository.save(board);
    }

    // 글 목록
    @Transactional(readOnly = true)
    public List<Board> list(Pageable pageable){
        Page<Board> all = boardRepository.findAll(pageable);

        return all.getContent();
    }

    // 글 상세보기
    @Transactional(readOnly=true)
    public Board detailView(int id){
        return boardRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다."));
    }

    @Transactional // 게시글 삭제하기
    public void deleteByBoard(int id){
        boardRepository.deleteById(id);
    }

    @Transactional // 게시글 수정하기.
    public void updateBoard(int id, Board requestBoard){
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다."));
        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());
        // 해당 함수로 종료시에 트랜잭션이 Service가 종료될 때 트랜잭션이 종료된다.
        // 이걸 더티체킹이라고 한다. - 자동 업데이트가 된다. DB FLUSH
    }

    @Transactional // 댓글 쓰기
    public void replyWrite(ReplySaveReqDto replySaveReqDto){
        replyRepository.mSave(replySaveReqDto.getUserId(), replySaveReqDto.getBoardId(), replySaveReqDto.getContent());
    }

    @Transactional
    public void deleteReply(int replyId){
        replyRepository.deleteById(replyId);
    }
}
