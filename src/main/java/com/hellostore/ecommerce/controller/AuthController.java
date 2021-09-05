package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.LoginDto;
import com.hellostore.ecommerce.dto.TokenDto;
import com.hellostore.ecommerce.dto.UserDto;
import com.hellostore.ecommerce.service.AuthService;
import javassist.bytecode.DuplicateMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@Valid @RequestBody UserDto userDto) throws DuplicateMemberException {
        return ResponseEntity.ok(authService.signup(userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<TokenDto> refreshToken(@RequestBody TokenDto tokenDto) {
        return ResponseEntity.ok(authService.refreshToken(tokenDto));
    }
}
