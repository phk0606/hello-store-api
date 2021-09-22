package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.ProductQnADto;
import com.hellostore.ecommerce.entity.Product;
import com.hellostore.ecommerce.entity.ProductQuestion;
import com.hellostore.ecommerce.repository.ProductQnARepository;
import com.hellostore.ecommerce.repository.ProductRepository;
import com.hellostore.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProductQnAService {

    private final ProductQnARepository productQnARepository;
    private final ProductRepository productRepository;

    @Transactional
    public void createProductQuestion(ProductQnADto productQnADto) {

        Product product = productRepository.getProduct(productQnADto.getProductId());

        ProductQuestion productQuestion
                = ProductQuestion.builder()
                .content(productQnADto.getQuestionContent())
                .product(product)
                .build();
        productQnARepository.createProductQuestion(productQuestion);
    }

    public Page<ProductQnADto> getProductQnA(Long productId, Pageable pageable) {
        return productQnARepository.getProductQnA(productId, pageable);
    }
}
