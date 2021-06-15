package com.kim.blog.service;

import com.kim.blog.model.RoleType;
import com.kim.blog.model.User;
import com.kim.blog.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@RequiredArgsConstructor
@Service
public class UserService {


    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;


    @Transactional // 회원가입 부분
    public void join(User user) {
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);
        user.signup(encPassword, RoleType.USER);
        userRepository.save(user);
    }

    @Transactional // 회원수정 부분
    public void updateUser(User user){
        User userEntity = userRepository.findById(user.getId())
                .orElseThrow(()-> new IllegalArgumentException("회원찾기실패"));

        // Validation체크 : 카카오 로그인을 한 유저는 패스워드 수정을 막아버린다(서버단) : 카카오 로그인 구현하면서 추가한 부분
        if(Objects.isNull(userEntity.getOauth()) || "".equals(userEntity.getOauth())){
            String rawPassword = user.getPassword();
            String encPassword = encoder.encode(rawPassword);

            user.changeInfo(encPassword,user.getEmail());
        }


    }

    @Transactional(readOnly=true) // 회원찾기 부분
    public User findUser(String username) {
//        return userRepository.findByUsername(username).orElseGet(User::new);
        return userRepository.findByUsername(username)
                .orElseGet(()-> new User());
    }
}
