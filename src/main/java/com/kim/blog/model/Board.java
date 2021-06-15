package com.kim.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.core.annotation.Order;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob // 대용량 데이터
    private String content;

    // 조회수
    private int count;

    @ManyToOne(fetch = FetchType.EAGER) // 보드가 매니, 유저는 원 : 하나의 유저는 여러개의 게시글을 쓸 수 있다.
    @JoinColumn(name="userId")
    private User user;
    // DB는 오브젝트 저장이 안 된다.
    // 고로 ForeignKey를 사용한다. 자바는 오브젝트를 저장할 수 있다.
    // 자바 프로그램에서 DB에 맞춰서 테이블을 생성한다.

    @OneToMany(mappedBy="board", fetch=FetchType.EAGER)
    // reply는 mappedBy 난 연관관계의 주인이 아니다.(난 Foreign Key가 아니다.)
    // DB에 컬럼을 생성하지 마라.
    @JsonIgnoreProperties({"board"}) // 무한참조를 막는 방법이다.
    @OrderBy("id desc")
    private List<Reply> replys;

    @CreationTimestamp
    private Timestamp timestamp;
}
