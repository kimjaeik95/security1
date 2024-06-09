package com.github.security11.config.auth;

//시큐리티가 로그인주소 요청이 오면 낚아채서 로그인ㅇ르 진행시킨다 .
//로그인을 진행이 완료가 되면 시큐리티 session 을 만들어준다 ( Security ContextHolder)
//오브젝트 타입 -> Authentication 타입객체
// Authentication 안에 유저 정보가 있어야됨.
// 유저 오브젝트타입 = > userDetails 타입 객체

import com.github.security11.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

//시큐리티가 가지고 있는 세션은 -> Authentication 객체 -> UserDetails(PrincipalDetails)
public class PrincipalDetails implements UserDetails {
    private final User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }
    //해당 유저의 권한을 리턴한느곳 .
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_" + user.getRole());

        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        //사이트 1년동안 회원이 로그인안하면 휴면계쩡으로하기로함
        //현재시간 -로긴시간 -> 1년을 초과하면 리턴을 false;
        return true;
    }
}
