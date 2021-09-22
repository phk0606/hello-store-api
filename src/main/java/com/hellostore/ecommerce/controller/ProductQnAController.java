package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.ProductQnADto;
import com.hellostore.ecommerce.service.ProductQnAService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productQnA")
@RequiredArgsConstructor
@Slf4j
public class ProductQnAController {

    private final ProductQnAService productQnAService;

    @PostMapping("/createProductQuestion")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void createProductQuestion(@RequestBody ProductQnADto productQnADto) {

        log.debug("productQnADto: {}", productQnADto);

        // 상품문의 저장
        productQnAService.createProductQuestion(productQnADto);
    }

    @PostMapping("/createProductAnswer")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void createProductAnswer(@RequestBody ProductQnADto productQnADto) {

        log.debug("productQnADto: {}", productQnADto);

        // 상품 답변 저장
        productQnAService.createProductAnswer(productQnADto);
    }

    @GetMapping("/getProductQnA")
    public Page<ProductQnADto> getProductQnA(Long productId, Pageable pageable) {
        return productQnAService.getProductQnA(productId, pageable);
    }
}
