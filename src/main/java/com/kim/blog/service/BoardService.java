package com.kim.blog.service;

import com.kim.blog.model.Board;
import com.kim.blog.model.RoleType;
import com.kim.blog.model.User;
import com.kim.blog.repository.BoardRepository;
import com.kim.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Transactional // 회원가입 부분
    public void write(Board board, User user) {
        board.setCount(0);
        board.setUser(user);
        boardRepository.save(board);
    }
    
    public List<Board> list(){
        return boardRepository.findAll();
    }

}
