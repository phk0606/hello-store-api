package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.ProductListDto;
import com.hellostore.ecommerce.dto.ProductSearchCondition;
import com.hellostore.ecommerce.dto.ShopProductDto;
import com.hellostore.ecommerce.repository.ShopProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ShopProductService {

    private final ShopProductRepository shopProductRepository;

    public Page<ShopProductDto> getProductsPageCondition(
            ProductSearchCondition productSearchCondition, Pageable pageable) throws IOException {
        Page<ShopProductDto> productsPage
                = shopProductRepository.getProductsPageCondition(productSearchCondition, pageable);

        for (ShopProductDto shopProductDto : productsPage.getContent()) {
            if(!ObjectUtils.isEmpty(shopProductDto.getImageId())) {
                shopProductDto.setImage(
                        Files.readAllBytes(
                                Paths.get(shopProductDto.getFilePath(),
                                        shopProductDto.getFileName())));
            }
        }

        return productsPage;
    }
}
