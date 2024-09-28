package com.desafio.profissional.magic.controller;

import com.desafio.profissional.magic.domain.User;
import com.desafio.profissional.magic.domain.record.UserLogin;
import com.desafio.profissional.magic.service.auth.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequestMapping("/login")
@RestController
public class AuthController {

    @Lazy
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity makeLogin(@RequestBody @Valid UserLogin user) {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.email(), user.password());
            Authentication authentication = manager.authenticate(token);
            String tokenAuth = tokenService.createToken((User) authentication.getPrincipal());
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/login")
                    .buildAndExpand(tokenAuth)
                    .toUri();
            return ResponseEntity.created(location).body(tokenAuth);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
