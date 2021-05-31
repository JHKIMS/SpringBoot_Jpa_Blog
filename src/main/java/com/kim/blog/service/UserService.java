package com.kim.blog.service;

import com.kim.blog.model.RoleType;
import com.kim.blog.model.User;
import com.kim.blog.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;


    @Transactional // 회원가입 부분
    public void join(User user) {
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);
        user.setPassword(encPassword);

        user.setRole(RoleType.USER);
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(User user){
        User persistance = userRepository.findById(user.getId())
                .orElseThrow(()->{
            return new IllegalArgumentException("회원찾기실패");
        });
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);
        persistance.setPassword(encPassword);
        persistance.setEmail(user.getEmail());

    }
}
