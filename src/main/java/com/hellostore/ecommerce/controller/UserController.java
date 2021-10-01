package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.UserDto;
import com.hellostore.ecommerce.dto.UserSearchCondition;
import com.hellostore.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
@Slf4j
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

    @GetMapping("/user/getUsers")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Page<UserDto> getUsers(UserSearchCondition userSearchCondition, Pageable pageable) {
        log.debug("userSearchCondition: {}, pageable: {}", userSearchCondition, pageable);
        return userService.getUsers(userSearchCondition, pageable);
    }

    @GetMapping("/user/getUsername")
    public ResponseEntity<String> getUsername(@RequestParam String name, @RequestParam String email) {
        return ResponseEntity.ok(userService.getUsername(name, email));
    }

    @PutMapping("/user/modifyUser")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void modifyUser(@RequestBody UserDto userDto) {
        userService.modifyUser(userDto);
    }

    @PutMapping("/user/modifyPerson")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void modifyPerson(@RequestBody UserDto userDto) {
        userService.modifyPerson(userDto);
    }

    @PutMapping("/user/modifyPassword")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void modifyPassword(@RequestBody UserDto userDto) {
        userService.modifyPassword(userDto);
    }
}
