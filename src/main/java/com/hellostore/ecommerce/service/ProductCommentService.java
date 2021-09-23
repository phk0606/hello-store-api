package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.ProductCommentDto;
import com.hellostore.ecommerce.dto.ProductCommentReplyDto;
import com.hellostore.ecommerce.entity.Product;
import com.hellostore.ecommerce.entity.ProductComment;
import com.hellostore.ecommerce.repository.ProductCommentImageRepository;
import com.hellostore.ecommerce.repository.ProductCommentReplyRepository;
import com.hellostore.ecommerce.repository.ProductCommentRepository;
import com.hellostore.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProductCommentService {

    private final ProductCommentRepository productCommentRepository;
    private final ProductCommentImageService productCommentImageService;
    private final ProductCommentReplyRepository productCommentReplyRepository;
    private final ProductCommentImageRepository productCommentImageRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void createProductComment(ProductCommentDto productCommentDto,
                                     List<MultipartFile> productCommentImages) {

        // 상품 조회

        Product product = productRepository.getProduct(productCommentDto.getProductId());

        ProductComment productComment = ProductComment.builder()
                .product(product)
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
