package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.TempPasswordDto;
import com.hellostore.ecommerce.dto.UserDto;
import com.hellostore.ecommerce.dto.UserSearchCondition;
import com.hellostore.ecommerce.entity.User;
import com.hellostore.ecommerce.repository.UserDslRepository;
import com.hellostore.ecommerce.repository.UserRepository;
import com.hellostore.ecommerce.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserDslRepository userDslRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

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
    public void createTempPasswordSendEmail(TempPasswordDto tempPasswordDto) {

        boolean userExist = userDslRepository.userExist(tempPasswordDto);

        if (!userExist) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No data");
        }

        String randomPassword = getRandomPassword(10);

        UserDto userDto = UserDto.builder().newPassword(randomPassword).build();
        userDslRepository.modifyPassword(userDto);

        tempPasswordDto.setTempPassword(randomPassword);
        emailService.sendEmail(tempPasswordDto);
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
            throw new BadCredentialsException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        userDslRepository.modifyPassword(userDto);
    }

    private String getRandomPassword(int size) {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '!', '@', '#', '$', '%', '^', '&'};
        StringBuffer sb = new StringBuffer();
        SecureRandom sr = new SecureRandom();
        sr.setSeed(new Date().getTime());
        int idx = 0;
        int len = charSet.length;
        for (int i = 0; i < size; i++) {
            // idx = (int) (len * Math.random());
            idx = sr.nextInt(len); // 강력한 난수를 발생시키기 위해 SecureRandom을 사용한다.
            sb.append(charSet[idx]);
        }
        return sb.toString();
    }
}
