package com.hellostore.ecommerce.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class CartProductDto {

    private Long cartProductId;
    private Long productId;
    private int quantity;

    private String firstOptionName;
    private String firstOptionValue;

    private String secondOptionName;
    private String secondOptionValue;

    private String productName;
    private int salePrice;

    @Setter
    private byte[] image;

    private String filePath;
    private String fileName;

    @QueryProjection
    public CartProductDto(Long cartProductId, Long productId, int quantity, String firstOptionName, String firstOptionValue, String secondOptionName, String secondOptionValue, String productName, int salePrice, String filePath, String fileName) {
        this.cartProductId = cartProductId;
        this.productId = productId;
        this.quantity = quantity;
        this.firstOptionName = firstOptionName;
        this.firstOptionValue = firstOptionValue;
        this.secondOptionName = secondOptionName;
        this.secondOptionValue = secondOptionValue;
        this.productName = productName;
        this.salePrice = salePrice;
        this.filePath = filePath;
        this.fileName = fileName;
    }
}
