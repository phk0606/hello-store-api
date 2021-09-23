package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.ProductCommentReplyDto;
import com.hellostore.ecommerce.entity.ProductComment;
import com.hellostore.ecommerce.entity.ProductCommentReply;
import com.hellostore.ecommerce.repository.ProductCommentReplyRepository;
import com.hellostore.ecommerce.repository.ProductCommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProductCommentReplyService {

    private final ProductCommentReplyRepository productCommentReplyRepository;
    private final ProductCommentRepository productCommentRepository;

    @Transactional
    public void createProductCommentReply(ProductCommentReplyDto productCommentReplyDto) {

        ProductComment productComment =
                productCommentRepository
                        .getProductCommentById(productCommentReplyDto.getProductCommentId());

        ProductCommentReply productCommentReply = ProductCommentReply.builder()
                .productComment(productComment)
                .content(productCommentReplyDto.getContent()).build();

        productCommentReplyRepository.save(productCommentReply);
    }

    public List<ProductCommentReplyDto> getProductCommentReplyList(Long productCommentId) {
        return productCommentReplyRepository.getProductCommentReplyList(productCommentId);
    }

    @Transactional
    public void modifyProductCommentReply(ProductCommentReplyDto productCommentReplyDto) {
        productCommentReplyRepository.modifyProductCommentReply(productCommentReplyDto);
    }

    @Transactional
    public void removeProductCommentReply(ProductCommentReplyDto productCommentReplyDto) {
        productCommentReplyRepository.removeProductCommentReply(productCommentReplyDto);
    }
}
