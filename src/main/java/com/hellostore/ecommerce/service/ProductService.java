package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.*;
import com.hellostore.ecommerce.entity.*;
import com.hellostore.ecommerce.enumType.ProductShowType;
import com.hellostore.ecommerce.repository.CategoryProductRepository;
import com.hellostore.ecommerce.repository.ProductImageRepository;
import com.hellostore.ecommerce.repository.ProductOptionRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private final ProductOptionRepository productOptionRepository;
    private final ProductImageRepository productImageRepository;

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

    public ProductModifyDto getProductById(Long id) throws IOException {
        ProductModifyDto productModifyDto = productRepository.getProductById(id);
        List<ProductOption> productOptions = productOptionRepository.getProductOptions(id);

        List<ProductOptionDto> productOptionDtos = new ArrayList<>();
        for (ProductOption productOption : productOptions) {
            productOptionDtos.add(new ProductOptionDto(productOption));
        }

        Map<String, List<ProductOptionDto>> collect = productOptionDtos.stream().collect(Collectors.groupingBy(ProductOptionDto::getOptionName));
        List<List<ProductOptionDto>> collect1 = collect.values().stream().collect(Collectors.toList());
        log.debug("collect1: {}", collect1.get(0));
        log.debug("collect2: {}", collect1.get(1));

        productModifyDto.setFirstOptions(collect1.get(0));
        productModifyDto.setSecondOptions(collect1.get(1));

        List<ProductImage> productImages = productImageRepository.getProductImages(id);

        List<ProductImageDto> productImageDtos = new ArrayList<>();

        for (ProductImage productImage : productImages) {
            ProductImageDto productImageDto = new ProductImageDto(productImage);
            productImageDto.setByteImage(
                    Files.readAllBytes(
                            Paths.get(productImage.getFilePath(), productImage.getFileName())));

            productImageDtos.add(productImageDto);
        }
        productModifyDto.setProductImageDtos(productImageDtos);

        return productModifyDto;
    }

//    public List<ProductCategoryImageDto> getProducts() throws IOException {
//
//        List<ProductCategoryImageDto> productCategoryImageDtos =
//                productRepository.getProducts();
//
//        for (ProductCategoryImageDto productCategoryImageDto : productCategoryImageDtos) {
//            if(!ObjectUtils.isEmpty(productCategoryImageDto.getImageId())) {
//                productCategoryImageDto.setImage(
//                        Files.readAllBytes(
//                                Paths.get(productCategoryImageDto.getFilePath(),
//                                        productCategoryImageDto.getFileName())));
//            }
//        }
//
//        return productCategoryImageDtos;
//    }

    public Page<ProductListDto> getProductsPage(
            ProductSearchCondition productSearchCondition, Pageable pageable) throws IOException {

        Page<ProductListDto> result =
                productRepository.getProductsPage(productSearchCondition, pageable);

        for (ProductListDto productCategoryImageDto : result.getContent()) {
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
