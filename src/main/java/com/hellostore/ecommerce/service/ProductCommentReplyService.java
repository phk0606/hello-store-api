package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.ProductCommentReplyDto;
import com.hellostore.ecommerce.entity.ProductComment;
import com.hellostore.ecommerce.entity.ProductCommentReply;
import com.hellostore.ecommerce.entity.User;
import com.hellostore.ecommerce.repository.ProductCommentReplyRepository;
import com.hellostore.ecommerce.repository.ProductCommentRepository;
import com.hellostore.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProductCommentReplyService {

    private final ProductCommentReplyRepository productCommentReplyRepository;
    private final ProductCommentRepository productCommentRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createProductCommentReply(ProductCommentReplyDto productCommentReplyDto) {

        ProductComment productComment =
                productCommentRepository
                        .getProductCommentById(productCommentReplyDto.getProductCommentId());
        Optional<User> username = userRepository.findByUsername(productCommentReplyDto.getUsername());

        ProductCommentReply productCommentReply = ProductCommentReply.builder()
                .productComment(productComment)
                .user(username.get()).content(productCommentReplyDto.getContent()).build();

        productCommentReplyRepository.save(productCommentReply);
    }

    public List<ProductCommentReplyDto> getProductCommentReplyList(Long productCommentId) {
        return productCommentReplyRepository.getProductCommentReplyList(productCommentId);
    }
}
