package com.github.security11.service;

import com.github.security11.dto.LoginResponseDto;
import com.github.security11.dto.UserRequestDto;
import com.github.security11.jwt.JwtFilter;
import com.github.security11.jwt.TokenProvider;
import com.github.security11.model.User;
import com.github.security11.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    public boolean join(UserRequestDto userRequestDto) {
        if (userRepository.existsByUsername(userRequestDto.getUsername())) {
            throw new IllegalArgumentException("중복된 유저이름 입니다.");
        }
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new IllegalArgumentException("중복된 이메일 입니다.");
        }
        User user = User.builder()
                .username(userRequestDto.getUsername())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .email(userRequestDto.getEmail())
                .role(userRequestDto.getRole())
                .build();

        userRepository.save(user);

        return true;
    }

    public String login(LoginResponseDto loginResponseDto) {
        String loginId = loginResponseDto.getUsername();
        String password =loginResponseDto.getPassword();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginId,password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return tokenProvider.createToken(authentication);


    }
}
