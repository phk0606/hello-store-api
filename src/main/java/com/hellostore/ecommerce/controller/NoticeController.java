package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.NoticeDto;
import com.hellostore.ecommerce.dto.NoticeSearchCondition;
import com.hellostore.ecommerce.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
@Slf4j
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping("/createNotice")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void createNotice(@RequestBody NoticeDto noticeDto) {
        log.debug("noticeDto: {}", noticeDto);
        noticeService.createNotice(noticeDto);
    }

    @PutMapping("/modifyNotice")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void modifyNotice(@RequestBody NoticeDto noticeDto) {
        noticeService.modifyNotice(noticeDto);
    }

    @DeleteMapping("/removeNotice")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void removeNotice(@RequestBody NoticeDto noticeDto) {
        noticeService.removeNotice(noticeDto);
    }

    @GetMapping("/getNotices")
    public Page<NoticeDto> getNotices(NoticeSearchCondition noticeSearchCondition,
                                      Pageable pageable) {
        log.debug("noticeSearchCondition: {}", noticeSearchCondition);
        return noticeService.getNotices(noticeSearchCondition, pageable);
    }

    @GetMapping("/getNotice")
    public NoticeDto getNotice(@RequestParam Long noticeId) {
        return noticeService.getNotice(noticeId);
    }
}
