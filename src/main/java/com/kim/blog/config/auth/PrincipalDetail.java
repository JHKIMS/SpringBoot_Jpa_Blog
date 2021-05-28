package com.kim.blog.config.auth;

import com.kim.blog.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;

/*스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를
* 스프링 시큐리티의 고유한 세션저장소에 저장을 해준다.
* 그때 저장되는게 PrincipalDetail이 저장이 된다.*/
public class PrincipalDetail implements UserDetails {

    private User user;

    public PrincipalDetail(User user){
        this.user=user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override // 계정이 만료되지 않았는지 리턴한다 ( true : 만료안됨 )
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override // true면 계정이 잠겨있지 않다는 것을 의미한다.
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override // 비밀번호의 만료되지 않았는지 리턴한다. True가 되어야 만료되지 않음
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override // 계정의 활성화인지 리턴한다( true : 활성화 ) 사용 가능
    public boolean isEnabled() {
        return true;
    }

    @Override  // 계정이 어떤 권한을 가지고 있는지 리턴을 한다.
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();

        collectors.add(()
                ->{return "ROLE_"+user.getRole();});

        return collectors;
    }
}
