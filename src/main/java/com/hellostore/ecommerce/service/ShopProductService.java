package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.ProductListDto;
import com.hellostore.ecommerce.dto.ProductOptionDto;
import com.hellostore.ecommerce.dto.ProductSearchCondition;
import com.hellostore.ecommerce.dto.ShopProductDto;
import com.hellostore.ecommerce.entity.ProductImage;
import com.hellostore.ecommerce.entity.ProductOption;
import com.hellostore.ecommerce.enumType.PointType;
import com.hellostore.ecommerce.enumType.ShippingFeeType;
import com.hellostore.ecommerce.repository.ProductImageRepository;
import com.hellostore.ecommerce.repository.ProductOptionRepository;
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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ShopProductService {

    private final ShopProductRepository shopProductRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductOptionRepository productOptionRepository;

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

    public ShopProductDto getProductById(Long productId) throws IOException {
        ShopProductDto product = shopProductRepository.getProductById(productId);

        if (product.getPointType().equals(PointType.DEFAULT)) {
            product.setPoint((product.getSalePrice() * 0.5) / 100);
        } else if (product.getPointType().equals(PointType.EACH)) {
            product.setPoint((product.getSalePrice() * product.getPointPerPrice()) / 100);
        }

        if (product.getShippingFeeType().equals(ShippingFeeType.DEFAULT)) {
            product.setShippingFee(2500);
        } else if (product.getShippingFeeType().equals(ShippingFeeType.EACH)) {
            product.setShippingFee(product.getEachShippingFee());
        }

        List<ProductImage> productImages = productImageRepository.getProductDetailImages(productId);

        List<byte[]> byteImages = new ArrayList<>();
        for (ProductImage productImage : productImages) {
            byteImages.add(Files.readAllBytes(Paths.get(productImage.getFilePath(),
                    productImage.getFileName())));
        }
        product.setByteImages(byteImages);

        List<ProductOption> productOptions1
                = productOptionRepository.getProductOptions(productId, 1);

        List<ProductOptionDto> productOptionDtos1 = new ArrayList<>();
        for (ProductOption productOption : productOptions1) {
            productOptionDtos1.add(new ProductOptionDto(productOption));
        }

        product.setFirstOptions(productOptionDtos1);

        List<ProductOption> productOptions2
                = productOptionRepository.getProductOptions(productId, 2);

        List<ProductOptionDto> productOptionDtos2 = new ArrayList<>();
        for (ProductOption productOption : productOptions2) {
            productOptionDtos2.add(new ProductOptionDto(productOption));
        }

        product.setSecondOptions(productOptionDtos2);

        return product;
    }
}
