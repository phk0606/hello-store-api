package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.PointHistoryDto;
import com.hellostore.ecommerce.dto.PointHistorySearchCondition;
import com.hellostore.ecommerce.service.PointHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pointHistory")
@RequiredArgsConstructor
@Slf4j
public class PointHistoryController {

    private final PointHistoryService pointHistoryService;

    @GetMapping("/getPointHistory")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Page<PointHistoryDto> getPointHistory(PointHistorySearchCondition pointHistorySearchCondition,
                                                 Pageable pageable) {
        log.debug("PointHistorySearchCondition: {}", pointHistorySearchCondition);
        return pointHistoryService.getPointHistory(pointHistorySearchCondition, pageable);
    }

    @GetMapping("/getUserHavePoint")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Integer getUserHavePoint(String username) {

        return pointHistoryService.getUserHavePoint(username);
    }
}
