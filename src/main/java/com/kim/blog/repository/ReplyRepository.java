package com.kim.blog.repository;

import com.kim.blog.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    @Modifying // 댓글 넣는거
    @Query(value = "Insert Into Reply(userId, boardId, content, createDate) Values(?,?,?,now())" , nativeQuery = true)
    void mSave(int userId, int boardId, String content);
}
