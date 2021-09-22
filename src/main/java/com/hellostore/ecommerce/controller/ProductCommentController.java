package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.ProductCommentDto;
import com.hellostore.ecommerce.dto.ProductCommentReplyDto;
import com.hellostore.ecommerce.service.ProductCommentReplyService;
import com.hellostore.ecommerce.service.ProductCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final ProductCommentReplyService productCommentReplyService;

    @PostMapping("/createProductComment")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void createProductComment(@RequestPart ProductCommentDto productCommentDto,
                                     @RequestParam(required = false)
                                             List<MultipartFile> productCommentImages) {

        log.debug("productCommentDto: {}", productCommentDto);

        // 상품평 저장
        productCommentService.createProductComment(productCommentDto, productCommentImages);
    }

    @PutMapping("/modifyProductComment")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void modifyProductComment(@RequestBody ProductCommentDto productCommentDto) {
        productCommentService.modifyProductComment(productCommentDto);
    }

    @DeleteMapping("/removeProductComment")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void removeProductComment(@RequestBody ProductCommentDto productCommentDto) {
        productCommentService.removeProductComment(productCommentDto.getProductCommentId());
    }

    @GetMapping("/getProductComments")
    public Page<ProductCommentDto> getProductComments(@RequestParam Long productId, Pageable pageable) {
        return productCommentService.getProductComments(productId, pageable);
    }

    @PostMapping("/createProductCommentReply")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void createProductCommentReply(
            @RequestBody ProductCommentReplyDto productCommentReplyDto) {

        log.debug("productCommentReplyDto: {}", productCommentReplyDto);

        // 상품평 댓글 저장
        productCommentReplyService.createProductCommentReply(productCommentReplyDto);
    }

    @GetMapping("/getProductCommentReplyList")
    public List<ProductCommentReplyDto> getProductCommentReplyList(Long productCommentId) {
        return productCommentReplyService.getProductCommentReplyList(productCommentId);
    }
}