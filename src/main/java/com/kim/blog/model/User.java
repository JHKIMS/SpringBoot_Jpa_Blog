package com.kim.blog.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity // User 클래스를 통해서 MySql에 테이블이 생성된다.
@DynamicInsert
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 프로젝트에 연결된 DB의 넘버링 전략을 따라간다. : 여기서는 auto_increment를 사용한다.
    private int id; // auto_increment

    @Column(nullable = false, length = 30)
    private String username;// 아이디

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    @Enumerated(EnumType.STRING)
    private RoleType role;  // Enum을 사용할 것이다.

    @CreationTimestamp // 시간이 자동으로 입력된다.
    private Timestamp createDate;
}
