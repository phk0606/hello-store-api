package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.UserDto;
import com.hellostore.ecommerce.entity.User;
import com.hellostore.ecommerce.service.UserService;
import javassist.bytecode.DuplicateMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<UserDto> getLoginUserInfo() {
        return ResponseEntity.ok(userService.getLoginUserInfo());
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserInfo(username));
    }
}
