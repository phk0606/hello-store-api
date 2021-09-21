package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.ProductCommentDto;
import com.hellostore.ecommerce.dto.ProductCommentReplyDto;
import com.hellostore.ecommerce.entity.OrderProduct;
import com.hellostore.ecommerce.entity.ProductComment;
import com.hellostore.ecommerce.entity.User;
import com.hellostore.ecommerce.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProductCommentService {

    private final ProductCommentRepository productCommentRepository;
    private final UserRepository userRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductCommentImageService productCommentImageService;
    private final ProductCommentReplyRepository productCommentReplyRepository;
    private final ProductCommentImageRepository productCommentImageRepository;

    @Transactional
    public void createProductComment(ProductCommentDto productCommentDto,
                                     List<MultipartFile> productCommentImages) {

        // 사용자 조회
        Optional<User> user = userRepository.findByUsername(productCommentDto.getUsername());
        // 주문 상품 조회
        OrderProduct orderProduct
                = orderProductRepository.getOrderProductById(productCommentDto.getOrderProductId());

        ProductComment productComment = ProductComment.builder()
                .orderProduct(orderProduct)
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

    @Transactional
    public void modifyProductComment(ProductCommentDto productCommentDto) {
        productCommentRepository.modifyProductComment(productCommentDto);
    }

    @Transactional
    public void removeProductComment(Long productCommentId) {

        productCommentReplyRepository.removeProductCommentReplies(productCommentId);
        productCommentImageRepository.removeProductCommentImages(productCommentId);
        productCommentRepository.removeProductComment(productCommentId);
    }

    public Page<ProductCommentDto> getProductComments(Long productId, Pageable pageable) {

        Page<ProductCommentDto> productComments
                = productCommentRepository.getProductComments(productId, pageable);

        Map<Long, List<ProductCommentReplyDto>> productCommentReplyMap
                = productCommentReplyRepository
                .getProductCommentReplies(toProductCommentIds(productComments.getContent()));

        productComments.forEach(
                o -> o.setProductCommentReplies(
                        productCommentReplyMap.get(o.getProductCommentId())));
        return productComments;
    }

    private List<Long> toProductCommentIds(List<ProductCommentDto> result) {
        return result.stream()
                .map(o -> o.getProductCommentId())
                .collect(Collectors.toList());
    }
}
