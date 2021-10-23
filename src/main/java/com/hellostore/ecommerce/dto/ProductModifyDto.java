package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.entity.Product;
import com.hellostore.ecommerce.entity.ProductImage;
import com.hellostore.ecommerce.entity.ProductOption;
import com.hellostore.ecommerce.enumType.PointType;
import com.hellostore.ecommerce.enumType.ProductShowType;
import com.hellostore.ecommerce.enumType.ShippingFeeType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class ProductModifyDto {

    private Long productId;
    private Long firstCategoryId;
    private Long secondCategoryId;

    @Setter
    private String productName;
    private int salePrice;
    private int regularPrice;
//    private int stockQuantity;
    private Integer maxPurchaseQuantity;
    private PointType pointType;
    private Double pointPerPrice;
    private ShippingFeeType shippingFeeType;
    private Integer eachShippingFee;
    private Boolean newArrival;
    private Boolean best;
    private Boolean discount;
    private String description;
    private String detailInfo;
    private String shippingInfo;
    private String exchangeReturnInfo;
    private ProductShowType productShowType;

    @Setter
    private List<ProductOptionDto> firstOptions = new ArrayList<>();
    @Setter
    private List<ProductOptionDto> secondOptions = new ArrayList<>();

    @Setter
    private List<ProductImageDto> productImageDtos = new ArrayList<>();

    @QueryProjection
    public ProductModifyDto(Long productId, Long firstCategoryId, Long secondCategoryId, String productName, int salePrice, int regularPrice, int maxPurchaseQuantity, PointType pointType, Double pointPerPrice, ShippingFeeType shippingFeeType, Integer eachShippingFee, Boolean newArrival, Boolean best, Boolean discount, String description, String detailInfo, String shippingInfo, String exchangeReturnInfo, ProductShowType productShowType) {
        this.productId = productId;
        this.firstCategoryId = firstCategoryId;
        this.secondCategoryId = secondCategoryId;
        this.productName = productName;
        this.salePrice = salePrice;
        this.regularPrice = regularPrice;
        this.maxPurchaseQuantity = maxPurchaseQuantity;
        this.pointType = pointType;
        this.pointPerPrice = pointPerPrice;
        this.shippingFeeType = shippingFeeType;
        this.eachShippingFee = eachShippingFee;
        this.newArrival = newArrival;
        this.best = best;
        this.discount = discount;
        this.description = description;
        this.detailInfo = detailInfo;
        this.shippingInfo = shippingInfo;
        this.exchangeReturnInfo = exchangeReturnInfo;
        this.productShowType = productShowType;
    }

    public Product toEntity(ProductModifyDto productModifyDto) {
        return Product.builder()
                .id(productModifyDto.getProductId())
                .name(productModifyDto.getProductName())
                .salePrice(productModifyDto.getSalePrice())
                .regularPrice(productModifyDto.getRegularPrice())
//                .stockQuantity(productModifyDto.getStockQuantity())
                .maxPurchaseQuantity(productModifyDto.getMaxPurchaseQuantity())
                .pointType(productModifyDto.getPointType())
                .pointPerPrice(productModifyDto.getPointPerPrice())
                .shippingFeeType(productModifyDto.getShippingFeeType())
                .eachShippingFee(productModifyDto.getEachShippingFee())
                .newArrival(productModifyDto.getNewArrival())
                .best(productModifyDto.getBest())
                .discount(productModifyDto.getDiscount())
                .detailInfo(productModifyDto.getDetailInfo())
                .shippingInfo(productModifyDto.getShippingInfo())
                .exchangeReturnInfo(productModifyDto.getExchangeReturnInfo())
                .productShowType(productModifyDto.getProductShowType())
                .build();
    }
}
