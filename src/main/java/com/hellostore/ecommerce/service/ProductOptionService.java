package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.entity.Product;
import com.hellostore.ecommerce.entity.ProductOption;
import com.hellostore.ecommerce.repository.ProductOptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProductOptionService {

    private final ProductOptionRepository productOptionRepository;

    @Transactional
    public List<ProductOption> createProductOption(List<ProductOption> productOptions, Product product) {

        List<ProductOption> productOptions1 = new ArrayList<>();
        for (ProductOption productOption : productOptions) {
            productOption.setProduct(product);
            ProductOption productOption1 = productOptionRepository.createProductOption(productOption);
            productOptions1.add(productOption1);
        }
        log.debug("productOptions1: {}", productOptions1);

        return productOptions1;
    }

    @Transactional
    public void removeProductOption(Long productId) {
        productOptionRepository.removeProductOption(productId);
    }
}
