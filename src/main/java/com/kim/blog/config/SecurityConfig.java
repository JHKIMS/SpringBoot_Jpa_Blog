package com.kim.blog.config;

import com.kim.blog.config.auth.PrincipalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration // 설정 파일이라고 알려주는 어노테이션
@EnableWebSecurity // 스프링 시큐리티가 활성화가 되어있는데, 설정을 이 어노테이션이 걸린 파일에서 하겠다.
@EnableGlobalMethodSecurity(prePostEnabled = true)// 특정 주소로 접근을 하면 권한 및 인증을 미리 체크한다는 뜻
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalDetailService principalDetailService; // 이거를 밑에 configure 메소드 부분에 넣어줘야지 비밀번호 해쉬 비교가 가능해진다.

    @Bean
    // 이 함수를 호출함으로써 BCryptPasswordEncoder를 받을 수 있다. + 해쉬 시켜준다.
    public BCryptPasswordEncoder encodePWD() {
        return new BCryptPasswordEncoder();
    }

    /*시큐리티가 대신 로그인해준다.
     * Password를 가로채는데, 해당 Password가 뭘로 해쉬가 되어 회원가입 되어있는지 알아야
     * 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교를 할 수 있다.*/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
    }

    // 여기 이 부분이 시큐리티의 로그인 필요없이 경로를 허용해주는 곳
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // csrf토큰 비활성화 ( 테스트시 걸어두는 것이 좋다.)
                .authorizeRequests()
                .antMatchers("/", "/auth/**", "/js/**", "/css/**", "image/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/auth/loginForm")
                .loginProcessingUrl("/auth/loginProc")// 스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인을 해준다.
                .defaultSuccessUrl("/"); // 로그인 성공시 해당 주소로 이동
    }



}
