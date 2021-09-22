package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.NoticeDto;
import com.hellostore.ecommerce.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
@Slf4j
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping("/createNotice")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void createNotice(@RequestBody NoticeDto noticeDto) {
        noticeService.createNotice(noticeDto);
    }
}
