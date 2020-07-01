package ru.ladmorph.todo.controller;

import lombok.Getter;
import lombok.Setter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.ladmorph.todo.dto.UserDto;
import ru.ladmorph.todo.security.jwt.JWTFilter;
import ru.ladmorph.todo.security.jwt.TokenProvider;

@RestController
@RequestMapping("/login")
public class AuthController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    public AuthController(TokenProvider tokenProvider,
                          AuthenticationManager authenticationManager) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public ResponseEntity<JWTToken> login(@RequestBody UserDto userDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication, false);
        
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer_" + token);

        return new ResponseEntity<>(new JWTToken(token), httpHeaders, HttpStatus.OK);
    }

    @Getter
    @Setter
    private static class JWTToken {
        private final String token;

        private JWTToken(String token) {
            this.token = token;
        }
    }
}
