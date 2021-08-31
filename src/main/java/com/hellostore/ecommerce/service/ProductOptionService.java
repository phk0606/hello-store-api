package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.entity.Product;
import com.hellostore.ecommerce.entity.ProductOption;
import com.hellostore.ecommerce.repository.ProductOptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProductOptionService {

    private final ProductOptionRepository productOptionRepository;

    @Transactional
    public void createProductOption(List<ProductOption> firstOptions, List<ProductOption> secondOptions, Product product) {

        for (ProductOption firstOption : firstOptions) {
            firstOption.setProduct(product);
            productOptionRepository.createProductOption(firstOption);
        }
        for (ProductOption secondOption : secondOptions) {
            secondOption.setProduct(product);
            productOptionRepository.createProductOption(secondOption);
        }
    }

    @Transactional
    public void removeProductOption(Long productId) {
        productOptionRepository.removeProductOption(productId);
    }
}
