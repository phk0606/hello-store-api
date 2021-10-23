package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.*;
import com.hellostore.ecommerce.entity.*;
import com.hellostore.ecommerce.enumType.ProductShowType;
import com.hellostore.ecommerce.repository.*;
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
    private final ProductImageRepository productImageRepository;
    private final StockQuantityRepository stockQuantityRepository;
    private final CartService cartService;
    private final OrderProductRepository orderProductRepository;
    private final ProductOptionRepository productOptionRepository;

    @Transactional
    public Product createProduct(ProductDto productDto, List<MultipartFile> productImages) throws IOException {

        // 카테고리 조회
        Category category = categoryService.getCategoryOne(productDto.getCategoryId());

        Product product = productDto.toEntity(productDto);

        categoryProductRepository.createCategoryProduct(
                CategoryProduct.builder()
                        .product(product)
                        .category(category).build());

        Product product1 = productRepository.createProduct(product);

        // 상품 옵션 저장
        productOptionService.createProductOption(productDto.getFirstOptions(), product1);
        productOptionService.createProductOption(productDto.getSecondOptions(), product1);

        // 상품 이미지 저장
        if(productImages != null) {
            productImageService.uploadProductImage(productImages, product1);
        }
        return product1;
    }

    public List<ProductSelectDto> getProductsByCategoryId(Long categoryId) {
        return productRepository.getProductsByCategoryId(categoryId);
    }

    public List<ProductSelectDto> getProducts() {
        return productRepository.getProducts();
    }

    @Transactional
    public void removeProducts(List<Long> productIds) throws IOException {

        boolean orderProductsExist = orderProductRepository.getOrderProductsExist(productIds);

        log.debug("orderProductsExist: {}", orderProductsExist);
        if (orderProductsExist) {
            throw new IllegalStateException("주문 상품이 존재하여 삭제할 수 없습니다.");
        }

        for (Long productId : productIds) {

            stockQuantityRepository.removeStockQuantity(productId);
            cartService.removeCartProducts(productId);
            categoryProductRepository.removeCategoryProduct(productId);
            productOptionService.removeProductOption(productId);
            productImageService.removeProductImage(productId);
            productRepository.removeProduct(productId);
        }
    }

    @Transactional
    public void modifyProduct(ProductDto productDto, List<MultipartFile> productImages) throws IOException {

        // 카테고리 수정
        categoryProductRepository.modifyCategoryProduct(productDto.getProductId(), productDto.getCategoryId());

        // 상품 수정
        Product product = productDto.toEntity(productDto);
        Product product1 = productRepository.modifyProduct(product);

        // 이미지 수정 (기존 것 삭제 후 신규 등록)
        if(productImages != null) {
            productImageService.removeProductImage(productDto.getProductId());
            productImageService.uploadProductImage(productImages, product1);
        }

        // TODO: 2021-10-09  재고 수량이 옵션에 의존되어 수정 방법 변경 필요 => 추가만 가능하게 수정
        // 옵션 수정 (기존 것 삭제 후 신규 등록)
        List<ProductOption> firstOptions = productDto.getFirstOptions();
        List<ProductOption> firstOptions1  = new ArrayList<>();
        for (ProductOption firstOption : firstOptions) {
            if (ObjectUtils.isEmpty(firstOption.getId())) {
                firstOptions1.add(firstOption);
            }
        }
        productOptionService.createProductOption(firstOptions1, product1);
        List<ProductOption> secondOptions = productDto.getSecondOptions();
        List<ProductOption> secondOptions1 = new ArrayList<>();
        for (ProductOption secondOption : secondOptions) {
            if (ObjectUtils.isEmpty(secondOption.getId())) {
                secondOptions1.add(secondOption);
            }
        }
        productOptionService.createProductOption(secondOptions1, product1);
//        productOptionRepository.removeProductOption(productDto.getProductId());
//        productOptionService.createProductOption(productDto.getFirstOptions(), product1);
//        productOptionService.createProductOption(productDto.getSecondOptions(), product1);

    }

    @Transactional
    public void modifyProductShowType(List<Long> productIds, ProductShowType productShowType) {
        productRepository.modifyProductShowType(productIds, productShowType);
    }

    public ProductModifyDto getProductById(Long id) throws IOException {
        ProductModifyDto productModifyDto = productRepository.getProductById(id);

        List<ProductOptionDto> productOptions1 = productOptionRepository.getProductOptions(id, 1);

        List<ProductOptionDto> productOptionDtos1 = new ArrayList<>();
        for (ProductOptionDto productOption : productOptions1) {
            productOptionDtos1.add(productOption);
        }

        productModifyDto.setFirstOptions(productOptionDtos1);

        List<ProductOptionDto> productOptions2 = productOptionRepository.getProductOptions(id, 2);

        List<ProductOptionDto> productOptionDtos2 = new ArrayList<>();
        for (ProductOptionDto productOption : productOptions2) {
            productOptionDtos2.add(productOption);
        }

        productModifyDto.setSecondOptions(productOptionDtos2);

        List<ProductImage> productImages = productImageRepository.getProductImages(id);

        List<ProductImageDto> productImageDtos = new ArrayList<>();

        for (ProductImage productImage : productImages) {
            ProductImageDto productImageDto = new ProductImageDto(productImage);
            productImageDto.setByteImage(
                    Files.readAllBytes(
                            Paths.get(productImage.getImageFile().getFilePath(), productImage.getImageFile().getFileName())));

            productImageDtos.add(productImageDto);
        }
        productModifyDto.setProductImageDtos(productImageDtos);

        return productModifyDto;
    }

    public Page<ProductListDto> getProductsPage(
            ProductSearchCondition productSearchCondition, Pageable pageable) {

        Page<ProductListDto> result =
                productRepository.getProductsPage(productSearchCondition, pageable);

        return result;
    }

    public Long getProductCount() {
        return productRepository.getProductCount();
    }
}
