package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.StyleShopNoticeDto;
import com.hellostore.ecommerce.service.StyleShopNoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final StyleShopNoticeService styleShopNoticeService;

    @GetMapping("/getCategoryNotice")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public StyleShopNoticeDto getCategoryNotice(Long categoryId) {

        return styleShopNoticeService.getCategoryNotice(categoryId);
    }

    @PostMapping("/mergeNoticeContent")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void mergeNoticeContent(@RequestBody StyleShopNoticeDto styleShopNoticeDto) {

        styleShopNoticeService.mergeNoticeContent(styleShopNoticeDto);
    }
}
