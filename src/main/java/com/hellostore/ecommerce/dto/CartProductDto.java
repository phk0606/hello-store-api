package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.enumType.PointType;
import com.hellostore.ecommerce.enumType.ShippingFeeType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class CartProductDto {

    private Long cartId;
    private Long cartProductId;
    private List<Long> cartProductIds;
    private Long productId;
    private int quantity;

    private String firstOptionName;
    private String firstOptionValue;

    private String secondOptionName;
    private String secondOptionValue;

    private String productName;
    private int salePrice;
    @Setter
    private int totalPrice;

    private PointType pointType;
    private int pointPerPrice;
    @Setter
    private double point;
    private ShippingFeeType shippingFeeType;
    private int eachShippingFee;
    @Setter
    private double shippingFee;

    @Setter
    private byte[] image;

    private String filePath;
    private String fileName;

    @QueryProjection
    public CartProductDto(Long cartId, Long cartProductId, Long productId, int quantity, String firstOptionName, String firstOptionValue, String secondOptionName, String secondOptionValue, String productName, int salePrice, int totalPrice, PointType pointType, int pointPerPrice, ShippingFeeType shippingFeeType, int eachShippingFee, String filePath, String fileName) {
        this.cartId = cartId;
        this.cartProductId = cartProductId;
        this.productId = productId;
        this.quantity = quantity;
        this.firstOptionName = firstOptionName;
        this.firstOptionValue = firstOptionValue;
        this.secondOptionName = secondOptionName;
        this.secondOptionValue = secondOptionValue;
        this.productName = productName;
        this.salePrice = salePrice;
        this.totalPrice = totalPrice;
        this.pointType = pointType;
        this.pointPerPrice = pointPerPrice;
        this.shippingFeeType = shippingFeeType;
        this.eachShippingFee = eachShippingFee;
        this.filePath = filePath;
        this.fileName = fileName;
    }
}
