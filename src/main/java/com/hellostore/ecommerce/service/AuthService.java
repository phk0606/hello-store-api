package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.LoginDto;
import com.hellostore.ecommerce.dto.TokenDto;
import com.hellostore.ecommerce.dto.UserDto;
import com.hellostore.ecommerce.entity.Address;
import com.hellostore.ecommerce.entity.Authority;
import com.hellostore.ecommerce.entity.RefreshToken;
import com.hellostore.ecommerce.entity.User;
import com.hellostore.ecommerce.jwt.TokenProvider;
import com.hellostore.ecommerce.repository.RefreshTokenRepository;
import com.hellostore.ecommerce.repository.UserRepository;
import javassist.bytecode.DuplicateMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public UserDto signup(UserDto userDto) throws DuplicateMemberException {
        if (userRepository
                .findByUsername(userDto.getUsername())
                .orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        Address address = new Address(userDto.getZoneCode(), userDto.getAddress(), userDto.getRoadAddress(), userDto.getDetailAddress());
        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .name(userDto.getName())
                .phoneNumber(userDto.getPhoneNumber())
                .address(address)
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return new UserDto().of(userRepository.save(user));
    }

    @Transactional
    public TokenDto login(LoginDto loginDto) {

        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.createToken(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .username(authentication.getName())
                .token(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return tokenDto;
    }

    @Transactional
    public TokenDto refreshToken(TokenDto tokenDto) {

        log.debug("tokenDto: {}", tokenDto);
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenDto.getRefreshToken(), null)) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 User ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenDto.getAccessToken());
        log.debug("authentication: {}", authentication);
        // 3. 저장소에서 User ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사

        log.debug("refreshToken 비교: {}, {}", refreshToken.getToken(), tokenDto.getRefreshToken());
        if (!refreshToken.getToken().equals(tokenDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto1 = tokenProvider.createToken(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto1.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto1;
    }
}
