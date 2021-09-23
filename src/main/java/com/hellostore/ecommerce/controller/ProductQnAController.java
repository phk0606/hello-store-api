package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.ProductQnADto;
import com.hellostore.ecommerce.dto.QnASearchCondition;
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

    @DeleteMapping("/removeQuestion")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void removeQuestion(@RequestBody ProductQnADto productQnADto) {

        log.debug("productQnADto: {}", productQnADto);
        productQnAService.removeQuestion(productQnADto);
    }

    @PutMapping("/modifyQuestion")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void modifyQuestion(@RequestBody ProductQnADto productQnADto) {

        productQnAService.modifyQuestion(productQnADto);
    }

    @DeleteMapping("/removeAnswer")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void removeAnswer(@RequestBody ProductQnADto productQnADto) {

        log.debug("productQnADto: {}", productQnADto);
        productQnAService.removeAnswer(productQnADto);
    }

    @PutMapping("/modifyAnswer")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void modifyAnswer(@RequestBody ProductQnADto productQnADto) {

        productQnAService.modifyAnswer(productQnADto);
    }

    @GetMapping("/getProductQnA")
    public Page<ProductQnADto> getProductQnA(QnASearchCondition qnASearchCondition, Pageable pageable) {
        log.debug("qnASearchCondition: {}",qnASearchCondition);
        return productQnAService.getProductQnA(qnASearchCondition, pageable);
    }
}
