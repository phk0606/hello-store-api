package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.ProductListDto;
import com.hellostore.ecommerce.dto.ProductModifyDto;
import com.hellostore.ecommerce.dto.ProductOptionDto;
import com.hellostore.ecommerce.dto.ProductSearchCondition;
import com.hellostore.ecommerce.entity.*;
import com.hellostore.ecommerce.enumType.PointType;
import com.hellostore.ecommerce.enumType.ProductShowType;
import com.hellostore.ecommerce.enumType.ShippingFeeType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = false)
@Slf4j
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryDslRepository categoryDslRepository;

    @Autowired
    ProductOptionRepository productOptionRepository;

    @Autowired CategoryProductRepository categoryProductRepository;

    @Autowired ProductImageRepository productImageRepository;


//    @Test
    void createProduct() {

        Product product = new Product("긴팔 티셔츠", 10000, 12000, 10, PointType.DEFAULT, 0, ShippingFeeType.DEFAULT, 0, true, false, true, "바디라인에 달라붙지 않아 시원하게 입기 좋은 데일리 반팔 티셔츠",  null, null, null, ProductShowType.SHOW);
        Product product1 = productRepository.createProduct(product);

        Category category1 = categoryDslRepository.getCategoryOne(11l);
        categoryProductRepository.createCategoryProduct(CategoryProduct.builder()
                .product(product)
                .category(category1).build());

        ProductOption productOption1 = new ProductOption(product1, "color", "black");
        ProductOption productOption2 = new ProductOption(product1, "color", "white");
        ProductOption productOption3 = new ProductOption(product1, "color", "blue");
        ProductOption productOption4 = new ProductOption(product1, "size", "large");
        ProductOption productOption5 = new ProductOption(product1, "size", "medium");
        ProductOption productOption6 = new ProductOption(product1, "size", "small");

        productOptionRepository.createProductOption(productOption1);
        productOptionRepository.createProductOption(productOption2);
        productOptionRepository.createProductOption(productOption3);
        productOptionRepository.createProductOption(productOption4);
        productOptionRepository.createProductOption(productOption5);
        productOptionRepository.createProductOption(productOption6);
    }

    @Test
    public void modifyProduct() {

//        Product product = Product.builder().name("노란티셔츠").build();
//        product.setId(31l);

        ProductModifyDto productModifyDto = productRepository.getProductById(38l);
        productModifyDto.setProductName("빨간 티셔츠");
        Product product = productModifyDto.toEntity(productModifyDto);

        productRepository.modifyProduct(product);
    }

    @Test
    public void createProductList() {
        for (int i = 0; i < 10; i++) {

            createProduct();
        }
    }

    @Test
    public void modifyProductShowType() {
        List<Long> productIds = new ArrayList<>();
        productIds.add(31l);
        productIds.add(38l);
        productIds.add(46l);
        productIds.add(54l);
        productRepository.modifyProductShowType(productIds, ProductShowType.HIDE);
    }

    @Test
    public void getProductById() {

        ProductModifyDto product = productRepository.getProductById(31l);

        log.debug("product: {}", product);

        List<ProductOptionDto> productOptions
                = productOptionRepository.getProductOptions(product.getProductId(), 1);
        log.debug("productOptions: {}", productOptions);

        List<ProductImage> productImages = productImageRepository.getProductImages(product.getProductId());
        log.debug("productImages: {}", productImages);
    }

    @Test
    public void getProductListPage() {

        PageRequest pageRequest = PageRequest.of(1, 3);
        ProductSearchCondition productSearchCondition = new ProductSearchCondition();
        productSearchCondition.setProductName("티셔츠");
        Page<ProductListDto> result
                = productRepository.getProductsPage(productSearchCondition, pageRequest);

        List<ProductListDto> content = result.getContent();
        for (ProductListDto productCategoryImageDto : content) {

            log.debug("productId: {}", productCategoryImageDto.getProductId());
        }
    }
}