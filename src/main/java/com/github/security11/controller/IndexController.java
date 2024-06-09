package com.github.security11.controller;

import com.github.security11.dto.LoginResponseDto;
import com.github.security11.dto.UserRequestDto;
import com.github.security11.jwt.JwtFilter;
import com.github.security11.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class IndexController {
    private final UserService userService;

    @GetMapping({"", "/"})
    public String index() {
        return "index";
    }

    @GetMapping("/user")
    public String user() {
        return "user";

    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public String manager() {
        return "manager";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginResponseDto loginResponseDto) {
         String token = userService.login(loginResponseDto);
         HttpHeaders httpHeaders = new HttpHeaders();
         httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER,"Bearer " + token);
         log.info("jwt 토큰 : {}" ,token);
        return new ResponseEntity<>(token,httpHeaders,HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(CsrfToken csrfToken) {

        //CSRF 토큰을 포함하여 응답
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(csrfToken.getHeaderName(), csrfToken.getToken());
        //클라이언트에게 리다이렉션할 URL
        String redirectUrl = "/login";

        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", redirectUrl)
                .headers(httpHeaders)
                .build();


    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "join";
    }
    @PostMapping("/join")
    public ResponseEntity<?> join (@RequestBody UserRequestDto userRequestDto) {
        boolean joins =userService.join(userRequestDto);
        return ResponseEntity.ok().body(joins);
    }
}