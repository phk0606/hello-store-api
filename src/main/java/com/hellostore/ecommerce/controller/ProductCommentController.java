package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.ProductCommentDto;
import com.hellostore.ecommerce.dto.ProductDto;
import com.hellostore.ecommerce.entity.Product;
import com.hellostore.ecommerce.service.ProductCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/productComment")
@RequiredArgsConstructor
@Slf4j
public class ProductCommentController {

    private final ProductCommentService productCommentService;

    @PostMapping("/createProductComment")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void createProductComment(@RequestPart ProductCommentDto productCommentDto,
                                     @RequestParam(required = false)
                                             List<MultipartFile> productCommentImages) {

        log.debug("productCommentDto: {}", productCommentDto);

        // 상품평 저장
        productCommentService.createProductComment(productCommentDto, productCommentImages);
    }

    @GetMapping("/getProductComments")
    public List<ProductCommentDto> getProductComments(@RequestParam Long productId) {
        return productCommentService.getProductComments(productId);
    }
}
