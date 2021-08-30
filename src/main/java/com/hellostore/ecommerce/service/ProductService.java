package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.ProductCategoryImageDto;
import com.hellostore.ecommerce.dto.ProductDto;
import com.hellostore.ecommerce.entity.Category;
import com.hellostore.ecommerce.entity.CategoryProduct;
import com.hellostore.ecommerce.entity.Product;
import com.hellostore.ecommerce.repository.CategoryProductRepository;
import com.hellostore.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryProductRepository categoryProductRepository;
    private final CategoryService categoryService;

    @Transactional
    public Product createProduct(ProductDto productDto) {

        // 카테고리 조회
        Category category = categoryService.getCategoryOne(productDto.getCategoryId());

        Product product = productDto.toEntity(productDto);

        categoryProductRepository.createCategoryProduct(
                CategoryProduct.builder()
                        .product(product)
                        .category(category).build());

        return productRepository.createProduct(product);
    }

    public List<ProductCategoryImageDto> getProducts() throws IOException {

        List<ProductCategoryImageDto> productCategoryImageDtos =
                productRepository.getProducts();

        for (ProductCategoryImageDto productCategoryImageDto : productCategoryImageDtos) {
            if(!ObjectUtils.isEmpty(productCategoryImageDto.getImageId())) {
                productCategoryImageDto.setImage(
                        Files.readAllBytes(
                                Paths.get(productCategoryImageDto.getFilePath(),
                                        productCategoryImageDto.getFileName())));
            }
        }

        return productCategoryImageDtos;
    }

    public Page<ProductCategoryImageDto> getProductsPage(Pageable pageable) throws IOException {

        Page<ProductCategoryImageDto> result =
                productRepository.getProductsPage(pageable);

        for (ProductCategoryImageDto productCategoryImageDto : result.getContent()) {
            if(!ObjectUtils.isEmpty(productCategoryImageDto.getImageId())) {
                productCategoryImageDto.setImage(
                        Files.readAllBytes(
                                Paths.get(productCategoryImageDto.getFilePath(),
                                        productCategoryImageDto.getFileName())));
            }
        }

        return result;
    }
}
