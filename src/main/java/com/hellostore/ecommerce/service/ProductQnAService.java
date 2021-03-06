package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.ProductQnADto;
import com.hellostore.ecommerce.dto.QnASearchCondition;
import com.hellostore.ecommerce.entity.Product;
import com.hellostore.ecommerce.entity.ProductAnswer;
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
import org.springframework.web.bind.annotation.RequestBody;

import static com.hellostore.ecommerce.entity.QProductAnswer.productAnswer;
import static com.hellostore.ecommerce.entity.QProductQuestion.productQuestion;

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

    @Transactional
    public void createProductAnswer(ProductQnADto productQnADto) {

        ProductQuestion productQuestion
                = productQnARepository.getProductQuestion(productQnADto.getProductQuestionId());

        ProductAnswer productAnswer = ProductAnswer.builder()
                .productQuestion(productQuestion)
                .content(productQnADto.getAnswerContent())
                .build();

        productQnARepository.createProductAnswer(productAnswer);
    }

    @Transactional
    public void removeQuestion(ProductQnADto productQnADto) {
        productQnARepository.removeQuestion(productQnADto);
    }

    @Transactional
    public void removeAnswer(ProductQnADto productQnADto) {
        productQnARepository.removeAnswer(productQnADto);
    }

    @Transactional
    public void modifyQuestion(ProductQnADto productQnADto) {

        productQnARepository.modifyQuestion(productQnADto);
    }

    @Transactional
    public void modifyAnswer(ProductQnADto productQnADto) {

        productQnARepository.modifyAnswer(productQnADto);
    }

    public Page<ProductQnADto> getProductQnA(QnASearchCondition qnASearchCondition, Pageable pageable) {
        return productQnARepository.getProductQnA(qnASearchCondition, pageable);
    }
}
