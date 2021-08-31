package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.dto.ProductCategoryImageDto;
import com.hellostore.ecommerce.dto.ProductSearchCondition;
import com.hellostore.ecommerce.entity.Category;
import com.hellostore.ecommerce.entity.CategoryProduct;
import com.hellostore.ecommerce.entity.Product;
import com.hellostore.ecommerce.entity.ProductOption;
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


//    @Test
    void createProduct() {

        Product product = new Product("긴팔 티셔츠", 10000, 12000, 10, PointType.DEFAULT, null, ShippingFeeType.DEFAULT, null, true, false, true, "바디라인에 달라붙지 않아 시원하게 입기 좋은 데일리 반팔 티셔츠",  null, null, null, ProductShowType.SHOW);
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
    public void removeProducts() {

    }

    @Test
    public void getProduct() {
        Product productById = productRepository.getProductById(199l);
        log.debug("productById: {}", productById);
    }

    @Test
    public void getProductList() {
        List<ProductCategoryImageDto> products = productRepository.getProducts();

        log.debug("products: {}, count: {}", products, products.size());
    }

    @Test
    public void getProductListPage() {

        PageRequest pageRequest = PageRequest.of(1, 3);
        ProductSearchCondition productSearchCondition = new ProductSearchCondition();
        productSearchCondition.setProductName("티셔츠");
        Page<ProductCategoryImageDto> result
                = productRepository.getProductsPage(productSearchCondition, pageRequest);

        List<ProductCategoryImageDto> content = result.getContent();
        for (ProductCategoryImageDto productCategoryImageDto : content) {

            log.debug("productId: {}", productCategoryImageDto.getProductId());
        }
    }
}