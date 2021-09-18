package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.UserDto;
import com.hellostore.ecommerce.entity.Authority;
import com.hellostore.ecommerce.entity.User;
import com.hellostore.ecommerce.repository.UserDslRepository;
import com.hellostore.ecommerce.repository.UserRepository;
import com.hellostore.ecommerce.util.SecurityUtil;
import javassist.bytecode.DuplicateMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserDslRepository userDslRepository;

    @Transactional(readOnly = true)
    public UserDto getUserInfo(String username) {
        return userRepository.findByUsername(username)
                .map(UserDto::of)
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
    }

    // 현재 SecurityContext 에 있는 유저 정보 가져오기
    @Transactional(readOnly = true)
    public UserDto getLoginUserInfo() {
        return userRepository.findByUsername(SecurityUtil.getCurrentUsername())
                .map(UserDto::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }

    public List<UserDto> getUsers() {
        return userDslRepository.getUsers();
    }
}
