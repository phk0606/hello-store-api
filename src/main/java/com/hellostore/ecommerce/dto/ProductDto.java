package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.entity.Product;
import com.hellostore.ecommerce.entity.ProductOption;
import com.hellostore.ecommerce.enumType.PointType;
import com.hellostore.ecommerce.enumType.ProductShowType;
import com.hellostore.ecommerce.enumType.ShippingFeeType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class ProductDto {

    private List<Long> productIds;
    private Long productId;
    private Long categoryId;
    private String name;
    private int salePrice;
    private int regularPrice;
    private Integer maxPurchaseQuantity;
    private int stockQuantity;
    private PointType pointType;
    private Integer pointPerPrice;
    private ShippingFeeType shippingFeeType;
    private Integer eachShippingFee;
    private Boolean newArrival;
    private Boolean best;
    private Boolean discount;
    private String description;

    private List<ProductOption> firstOptions = new ArrayList<>();
    private List<ProductOption> secondOptions = new ArrayList<>();

    private String detailInfo;
    private String shippingInfo;
    private String exchangeReturnInfo;

    private ProductShowType productShowType;

    private int clickCount;

    public Product toEntity(ProductDto productDto) {

        return Product.builder()
                .id(productDto.getProductId())
                .name(productDto.getName())
                .salePrice(productDto.getSalePrice())
                .regularPrice(productDto.getRegularPrice())
                .stockQuantity(productDto.getStockQuantity())
                .maxPurchaseQuantity(productDto.getMaxPurchaseQuantity())
                .pointType(productDto.getPointType())
                .pointPerPrice(productDto.getPointPerPrice())
                .shippingFeeType(productDto.getShippingFeeType())
                .eachShippingFee(productDto.getEachShippingFee())
                .newArrival(productDto.getNewArrival())
                .best(productDto.getBest())
                .discount(productDto.getDiscount())
                .description(productDto.getDescription())
                .detailInfo(productDto.getDetailInfo())
                .shippingInfo(productDto.getShippingInfo())
                .exchangeReturnInfo(productDto.getExchangeReturnInfo())
                .productShowType(productDto.getProductShowType())
                .build();
    }
}
