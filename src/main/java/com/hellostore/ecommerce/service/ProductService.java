package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.ProductCategoryImageDto;
import com.hellostore.ecommerce.dto.ProductDto;
import com.hellostore.ecommerce.entity.Category;
import com.hellostore.ecommerce.entity.CategoryProduct;
import com.hellostore.ecommerce.entity.Product;
import com.hellostore.ecommerce.enumType.ProductShowType;
import com.hellostore.ecommerce.repository.CategoryProductRepository;
import com.hellostore.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

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
    private final ProductOptionService productOptionService;
    private final ProductImageService productImageService;

    @Transactional
    public Product createProduct(ProductDto productDto, List<MultipartFile> productImages) {

        // 카테고리 조회
        Category category = categoryService.getCategoryOne(productDto.getCategoryId());

        Product product = productDto.toEntity(productDto);

        categoryProductRepository.createCategoryProduct(
                CategoryProduct.builder()
                        .product(product)
                        .category(category).build());

        Product product1 = productRepository.createProduct(product);
        // 상품 옵션 저장
        productOptionService.createProductOption(productDto.getFirstOptions(), productDto.getSecondOptions(), product1);

        // 상품 이미지 저장
        productImageService.uploadProductImage(productImages, product1);
        return product1;
    }

    @Transactional
    public void removeProducts(List<Long> productIds) {

        for (Long productId : productIds) {

            categoryProductRepository.removeCategoryProduct(productId);
            productOptionService.removeProductOption(productId);
            productImageService.removeProductImage(productId);
            productRepository.removeProduct(productId);
        }
    }

    @Transactional
    public void modifyProductShowType(List<Long> productIds, ProductShowType productShowType) {
        productRepository.modifyProductShowType(productIds, productShowType);
    }

    public Product getProductById(Long id) {
        return productRepository.getProductById(id);
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
