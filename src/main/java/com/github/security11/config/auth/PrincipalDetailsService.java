package com.github.security11.config.auth;

import com.github.security11.model.User;
import com.github.security11.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
// 시큐리티 설정에서  (/login);
//login 요청이 오면 자동으로 UserDetailService 타입으로 Ioc  되어있는 loadUserByUserName 함수가 실행
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
   private final UserRepository userRepository;

   //시큐리ㅣ티 세션ㅐ(Authentication)내부 (UserDetails) 내부 )
   @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return new PrincipalDetails(userEntity);

    }
}
