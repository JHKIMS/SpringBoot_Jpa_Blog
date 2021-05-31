package com.kim.blog.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 이 주소로 허용한다.
// 그냥 주소가 / 이면 index.jsp 허용한다.
// static 이하에 있는 css, js, image 를 허용한다.

@Controller
public class UserController {

    @GetMapping("/auth/joinForm")
    public String joinForm() {
        System.out.println("이 부분이 회원가입 1경로");
        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm() {
        System.out.println("로그인폼 부분이다.");
        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }

    @GetMapping("/auth/kakao/callback")
    public @ResponseBody String kakaoCallback(String code){

        RestTemplate rt = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 오브젝트 생성
       MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code"); // 보통 value값은 변수화시켜서 사용해야한다.
        params.add("client_id", "948dfcab19b378a4b34564c781522a30");
        params.add("redirect_uri", "http://localhost:8080/auth/kakao/callback");
        params.add("code", code);

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);


        // HTTP 요청하기 - response변수의 응답 받음
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",  // 토큰 발급 요청 주소
                HttpMethod.POST, // Http 메소드 방식
                kakaoTokenRequest, // HTTP 바디에 들어갈 데이터와 헤더값
                String.class // 응답 받을 데이터 타입
        );

//        return "카카오 인증 완료 : 코드값 - "+code;
        return "카카오 토큰 요청 완료 : 토큰요청에 대한 응답: "+response;
    }
}
