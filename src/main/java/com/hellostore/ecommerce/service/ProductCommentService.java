package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.ProductCommentDto;
import com.hellostore.ecommerce.entity.Product;
import com.hellostore.ecommerce.entity.ProductComment;
import com.hellostore.ecommerce.entity.User;
import com.hellostore.ecommerce.repository.ProductCommentRepository;
import com.hellostore.ecommerce.repository.ProductRepository;
import com.hellostore.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProductCommentService {

    private final ProductCommentRepository productCommentRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductCommentImageService productCommentImageService;

    @Transactional
    public void createProductComment(ProductCommentDto productCommentDto,
                                     List<MultipartFile> productCommentImages) {

        // 사용자 조회
        Optional<User> user = userRepository.findByUsername(productCommentDto.getUsername());
        // 상품 조회
        Product product = productRepository.getProduct(productCommentDto.getProductId());

        ProductComment productComment = ProductComment.builder()
                .product(product)
                .user(user.get())
                .content(productCommentDto.getContent())
                .grade(productCommentDto.getGrade())
                .build();

        ProductComment productComment1 = productCommentRepository.save(productComment);

        // 상품평 이미지 저장
        if(productCommentImages != null) {
            productCommentImageService.uploadProductCommentImage(productCommentImages, productComment1);
        }
    }
}
