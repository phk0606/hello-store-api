package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.ProductDto;
import com.hellostore.ecommerce.dto.ProductListDto;
import com.hellostore.ecommerce.dto.ProductModifyDto;
import com.hellostore.ecommerce.dto.ProductSearchCondition;
import com.hellostore.ecommerce.entity.Product;
import com.hellostore.ecommerce.enumType.ProductShowType;
import com.hellostore.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;


    @PostMapping("/createProduct")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void createProduct(@RequestPart ProductDto productDto, @RequestParam(required = false) List<MultipartFile> productImages) {

        log.debug("productDto: {}", productDto);

        // 상품 저장
        Product product = productService.createProduct(productDto, productImages);
    }

    @DeleteMapping("/removeProducts")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void removeProducts(@RequestBody ProductDto productDto) {

        List<Long> productIds = productDto.getProductIds();
        log.debug("removeProducts: {}", productIds);
        productService.removeProducts(productIds);
    }

    @PutMapping("/modifyProduct")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void modifyProduct(@RequestPart ProductDto productDto, @RequestParam(required = false) List<MultipartFile> productImages) {

        productService.modifyProduct(productDto, productImages);
    }

    @PutMapping("/modifyProductShowType")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void modifyProductShowType(@RequestBody ProductDto productDto) {

        List<Long> productIds = productDto.getProductIds();
        ProductShowType productShowType = productDto.getProductShowType();

        log.debug("productIds: {}, productShowType: {}", productIds, productShowType);
        productService.modifyProductShowType(productIds, productShowType);
    }

    @GetMapping("/getProductById")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ProductModifyDto getProductById(@RequestParam Long productId) throws IOException {
        return productService.getProductById(productId);
    }

    @GetMapping("/getProductsPageCondition")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Page<ProductListDto> getProductsPageCondition(
            ProductSearchCondition productSearchCondition, Pageable pageable) throws IOException {

        return productService.getProductsPage(productSearchCondition, pageable);
    }

}
