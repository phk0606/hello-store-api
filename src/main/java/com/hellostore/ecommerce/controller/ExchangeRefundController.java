package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.ExchangeRefundDto;
import com.hellostore.ecommerce.dto.ExchangeRefundSearchCondition;
import com.hellostore.ecommerce.service.ExchangeRefundService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/exchangeRefund")
@RequiredArgsConstructor
@Slf4j
public class ExchangeRefundController {

    private final ExchangeRefundService exchangeRefundService;

    @PostMapping("/createExchangeRefund")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void createExchangeRefund(@RequestPart ExchangeRefundDto exchangeRefundDto,
                                     @RequestParam(required = false)
                                             List<MultipartFile> exchangeRefundImages) {

        log.debug("exchangeRefundDto: {}", exchangeRefundDto);

        // 상품평 저장
        exchangeRefundService.createExchangeRefund(exchangeRefundDto, exchangeRefundImages);
    }

    @GetMapping("/getExchangeRefunds")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Page<ExchangeRefundDto> getExchangeRefunds (
            ExchangeRefundSearchCondition exchangeRefundSearchCondition, Pageable pageable) {

        return exchangeRefundService.getExchangeRefunds(exchangeRefundSearchCondition, pageable);
    }
}
