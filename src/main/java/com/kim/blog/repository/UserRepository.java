package com.kim.blog.repository;

import com.kim.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


    Optional<User> findByUsername(String username);
    // select * from user where username=1? 이 쿼리가 실행된다.

}
