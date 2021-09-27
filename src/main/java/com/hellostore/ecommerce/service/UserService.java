package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.UserDto;
import com.hellostore.ecommerce.dto.UserSearchCondition;
import com.hellostore.ecommerce.entity.Authority;
import com.hellostore.ecommerce.entity.User;
import com.hellostore.ecommerce.repository.UserDslRepository;
import com.hellostore.ecommerce.repository.UserRepository;
import com.hellostore.ecommerce.util.SecurityUtil;
import javassist.bytecode.DuplicateMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
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
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserDto getUserInfo(String username) {
        return userDslRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
    }

    // 현재 SecurityContext 에 있는 유저 정보 가져오기
    @Transactional(readOnly = true)
    public UserDto getLoginUserInfo() {
        return userRepository.findByUsername(SecurityUtil.getCurrentUsername())
                .map(UserDto::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }

    public Page<UserDto> getUsers(UserSearchCondition userSearchCondition, Pageable pageable) {
        return userDslRepository.getUsers(userSearchCondition, pageable);
    }

    @Transactional
    public void modifyUser(UserDto userDto) {
        userDslRepository.modifyUser(userDto);
    }

    public String getUsername(String name, String email) {
        return userDslRepository.getUsername(name, email);
    }

    @Transactional
    public void modifyPerson(UserDto userDto) {
        userDslRepository.modifyPerson(userDto);
    }

    @Transactional
    public void modifyPassword(UserDto userDto) {
        Optional<User> user = userRepository.findByUsername(userDto.getUsername());

        boolean matches = passwordEncoder.matches(userDto.getPassword(), user.get().getPassword());
        if (!matches) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        userDslRepository.modifyPassword(userDto);
    }
}
