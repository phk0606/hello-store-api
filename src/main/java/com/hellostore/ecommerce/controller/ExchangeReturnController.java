package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.ExchangeReturnDto;
import com.hellostore.ecommerce.dto.ExchangeReturnSearchCondition;
import com.hellostore.ecommerce.service.ExchangeReturnService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/exchangeReturn")
@RequiredArgsConstructor
@Slf4j
public class ExchangeReturnController {

    private final ExchangeReturnService exchangeReturnService;

    @PostMapping("/createExchangeReturn")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void createExchangeReturn(@RequestPart ExchangeReturnDto exchangeReturnDto,
                                     @RequestParam(required = false)
                                             List<MultipartFile> exchangeReturnImages) {

        log.debug("exchangeReturnDto: {}", exchangeReturnDto);

        exchangeReturnService.createExchangeReturn(exchangeReturnDto, exchangeReturnImages);
    }

    @GetMapping("/getExchangeReturns")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Page<ExchangeReturnDto> getExchangeReturns (
            ExchangeReturnSearchCondition exchangeReturnSearchCondition, Pageable pageable) {

        return exchangeReturnService.getExchangeReturns(exchangeReturnSearchCondition, pageable);
    }

    @GetMapping("/getExchangeReturn")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ExchangeReturnDto getExchangeReturn (@RequestParam Long exchangeReturnId) {

        return exchangeReturnService.getExchangeReturn(exchangeReturnId);
    }

    @PutMapping("/modifyExchangeReturnStatus")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void modifyExchangeReturnStatus(@RequestBody ExchangeReturnDto exchangeReturnDto) {

        exchangeReturnService.modifyExchangeReturnStatus(
                exchangeReturnDto.getExchangeReturnIds(), exchangeReturnDto.getExchangeReturnStatus());
    }

    @PutMapping("/modifyExchangeReturn")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void modifyExchangeReturn(@RequestBody ExchangeReturnDto exchangeReturnDto) {
        exchangeReturnService.modifyExchangeReturn(exchangeReturnDto);
    }

    @DeleteMapping("/removeExchangeReturn")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void removeExchangeReturn(@RequestBody ExchangeReturnDto exchangeReturnDto) {
        exchangeReturnService.removeExchangeReturn(exchangeReturnDto);
    }
}
