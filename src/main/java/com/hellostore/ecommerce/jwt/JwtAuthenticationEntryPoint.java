package com.hellostore.ecommerce.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response
            , AuthenticationException authException) throws IOException {

        String expired = (String) request.getAttribute("expired");
        log.debug("expired message: {}", expired);
        if (expired != null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, expired);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "아이디 또는 비밀번호가 일치하지 않습니다.");
        }
    }
}
