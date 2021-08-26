package com.hellostore.ecommerce.entity;

import com.hellostore.ecommerce.enumType.PointType;
import com.hellostore.ecommerce.enumType.ProductShowType;
import com.hellostore.ecommerce.enumType.ShippingFeeType;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private Long id;

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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductOption> productOptions = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> productImages = new ArrayList<>();

//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
//    private List<CategoryProduct> categoryProducts = new ArrayList<>();

    @Lob
    private String detailInfo;

    @Lob
    private String shippingInfo;

    @Lob
    private String exchangeReturnInfo;

    @Enumerated
    private ProductShowType productShowType;


    @Builder
    public Product(String name, int salePrice, int regularPrice, int maxPurchaseQuantity, PointType pointType, Integer pointPerPrice, ShippingFeeType shippingFeeType, Integer eachShippingFee, Boolean newArrival, Boolean best, Boolean discount, String description, List<ProductOption> productOptions, List<ProductImage> productImages,  String detailInfo, String shippingInfo, String exchangeReturnInfo, ProductShowType productShowType) {

        this.name = name;
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
        this.productOptions = productOptions;
        this.productImages = productImages;
//        this.categoryProducts = categoryProducts;
        this.detailInfo = detailInfo;
        this.shippingInfo = shippingInfo;
        this.exchangeReturnInfo = exchangeReturnInfo;
        this.productShowType = productShowType;
    }
}
