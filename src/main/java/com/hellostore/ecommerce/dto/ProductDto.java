package com.hellostore.ecommerce.dto;

import com.hellostore.ecommerce.entity.Category;
import com.hellostore.ecommerce.entity.Product;
import com.hellostore.ecommerce.entity.ProductImage;
import com.hellostore.ecommerce.entity.ProductOption;
import com.hellostore.ecommerce.enumType.PointType;
import com.hellostore.ecommerce.enumType.ProductShowType;
import com.hellostore.ecommerce.enumType.ShippingFeeType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
@Slf4j
public class ProductDto {

    private Long category1Id;
    private Long category2Id;
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

    private List<ProductOption> firstOptions = new ArrayList<>();
    private List<ProductOption> secondOptions = new ArrayList<>();

    private String detailInfo;
    private String shippingInfo;
    private String exchangeReturnInfo;

    private ProductShowType productShowType;

    public Product toEntity(ProductDto productDto) {

        return Product.builder()
                .name(productDto.getName())
                .salePrice(productDto.getSalePrice())
                .regularPrice(productDto.getRegularPrice())
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
                .productShowType(productDto.getProductShowType()).build();
    }
}
