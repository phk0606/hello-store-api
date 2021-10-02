package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.TempPasswordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendEmail(TempPasswordDto tempPasswordDto) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("phk0606@naver.com");
        simpleMailMessage.setTo(tempPasswordDto.getEmailAddress());
        simpleMailMessage.setSubject("헬로 스토어 임시 비밀번호 발송");
        simpleMailMessage.setText("임시 비밀 번호: ".concat(tempPasswordDto.getTempPassword()));
        javaMailSender.send(simpleMailMessage);
    }
}
