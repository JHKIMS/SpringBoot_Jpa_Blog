package com.kim.blog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kim.blog.model.KakaoProfile;
import com.kim.blog.model.ReqUserToken;
import com.kim.blog.model.User;
import com.kim.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 이 주소로 허용한다.
// 그냥 주소가 / 이면 index.jsp 허용한다.
// static 이하에 있는 css, js, image 를 허용한다.
@RequiredArgsConstructor
@Controller
public class UserController {


    @Value("${hoon.key}")
    private String hoonKey;

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

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




    /*-----------------------------카카오 로그인 스타트;;;--------------------------------------------------------------------------*/

    @GetMapping("/auth/kakao/callback") // 이쪽으로 카카오 로그인을 받을 것이다.
    public String kakaoCallback(String code) { // code값은 카카오에서 주는 것 => 이 코드가 인증code이다.

        // ★★★ 인증code를 통해 사용자 토큰을 받는 과정이다. ★★★

        RestTemplate rt = new RestTemplate();  // RestTemplate를 쓰면 Http 요청이 편해진다.

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code"); // 보통 value값은 변수화시켜서 사용해야한다.
        params.add("client_id", "948dfcab19b378a4b34564c781522a30");
        params.add("redirect_uri", "http://localhost:8080/auth/kakao/callback");
        params.add("code", code);

        // HttpHeader와 HttpBody를  ★★★하나의 오브젝트에 담기★★★
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);


        // HTTP 요청하기 - response변수의 응답 받음
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",  // 토큰 발급 요청 주소
                HttpMethod.POST, // Http 메소드 방식
                kakaoTokenRequest, // HTTP 바디에 들어갈 데이터와 헤더값
                String.class // 응답 받을 데이터 타입
        );


        // OAuthToken(카카오에서 받은 사용자 토큰) 데이터 처리
        ObjectMapper objectMapper = new ObjectMapper();

        ReqUserToken oauthToken = null;

        try {
            oauthToken = objectMapper.readValue(response.getBody(), ReqUserToken.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println("카카오에서 받은 액세스 토큰 == : " + oauthToken.getAccess_token());

        /*-------------------------------------------------------------------*/

        // rt2는 카카오에서 받은 액세스 토큰을 사용해서 사용자 정보를 조회하는 것.

        RestTemplate rt2 = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader를 객체에 담아주자.
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 =
                new HttpEntity<>(headers2);


        // HTTP 요청하기 - response변수의 응답 받음
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",  // 토큰 발급 요청 주소
                HttpMethod.POST, // Http 메소드 방식
                kakaoProfileRequest2, // HTTP 바디에 들어갈 데이터와 헤더값
                String.class // 응답 받을 데이터 타입
        );

        // => 여기까지 만들고 return을 한 번 받은 다음에,
        // 그 데이터들을 jsonschema2pojo 사이트를 이용해서 model의 KakaoProfile로 만들자.
        // 카멜 표기법으로 나오는데, 이거를 다 언더스코프로 바꿔줘야 하니 주의해야 한다.


        // 그리고 이제 KakaoProfile에 집어넣을 것이다.
        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;

        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


//        System.out.println("카카오 아이디 번호" + kakaoProfile.getId());
//        System.out.println("카카오 이메일" + kakaoProfile.getKakao_account().getEmail());

        // 카카오 유저 아이디 생성하기
//        System.out.println("카카오로 로그인한 블로그 유저 아이디: " + kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId());
//        System.out.println("카카오로 로그인한 블로그 유저 이메일: " + kakaoProfile.getKakao_account().getEmail());
//        System.out.println("카카오로 로그인한 블로그 유저 패스워드: " + garbagePassword);

        User kakaoUser = User.builder()
                .username(kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId())
                .password(hoonKey)
                .email(kakaoProfile.getKakao_account().getEmail())
                .oauth("kakao")
                .build();

        // 기존 가입자인지 아닌지 확인해서 처리한다.
        User originUser = userService.findUser(kakaoUser.getUsername());

        // 비가입자일 경우
        if(originUser.getUsername() == null){
            userService.join(kakaoUser);
        }

        // 로그인 처리
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), hoonKey));
        SecurityContextHolder.getContext().setAuthentication(authentication);

//        System.out.println("response2의 값은 : " + response2.getBody());
//        System.out.println("카카오 인증 완료-code 값은 : "+code);
//        System.out.println("response.getBody의 값은: "+response.getBody());

        return "redirect:/";
    }
}
