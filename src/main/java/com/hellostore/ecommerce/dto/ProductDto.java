package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.entity.ProductImage;
import com.hellostore.ecommerce.entity.ProductOption;
import com.hellostore.ecommerce.enumType.PointType;
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
@Slf4j
public class ProductDto {

    private String name;

    private Integer salePrice;
    private Integer regularPrice;

    private Integer maxPurchaseQuantity;

    private PointType pointType;
    private Integer pointPerPrice;

    private ShippingFeeType shippingFeeType;

    private Integer eachShippingFee;

    private Boolean newArrival;
    private Boolean best;
    private Boolean discount;

    private String description;

    private List<ProductOption> productOptions = new ArrayList<>();
    private List<ProductImage> productImages = new ArrayList<>();
}
