package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.StyleShopNoticeDto;
import com.hellostore.ecommerce.service.StyleShopNoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/styleShopNotice")
@RequiredArgsConstructor
@Slf4j
public class StyleShopNoticeController {

    private final StyleShopNoticeService styleShopNoticeService;

    @GetMapping("/getCategoryNotice")
    public StyleShopNoticeDto getCategoryNotice(Long categoryId) {

        return styleShopNoticeService.getCategoryNotice(categoryId);
    }

    @PostMapping("/mergeNoticeContent")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void mergeNoticeContent(@RequestBody StyleShopNoticeDto styleShopNoticeDto) {

        styleShopNoticeService.mergeNoticeContent(styleShopNoticeDto);
    }
}
