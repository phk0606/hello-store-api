package com.hellostore.ecommerce.entity;

import com.hellostore.ecommerce.enumType.PointType;
import com.hellostore.ecommerce.enumType.ProductShowType;
import com.hellostore.ecommerce.enumType.ShippingFeeType;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

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

    @Enumerated(EnumType.STRING)
    private PointType pointType;
    private Integer pointPerPrice;

    @Enumerated(EnumType.STRING)
    private ShippingFeeType shippingFeeType;

    private Integer eachShippingFee;

    private Boolean newArrival;
    private Boolean best;
    private Boolean discount;

    private String description;

    @Column(columnDefinition = "integer default 0")
    private Integer clickCount;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductOption> productOptions = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> productImages = new ArrayList<>();


    @Lob
    private String detailInfo;

    @Lob
    private String shippingInfo;

    @Lob
    private String exchangeReturnInfo;

    @Enumerated(EnumType.STRING)
    private ProductShowType productShowType;


    @Builder
    public Product(String name, int salePrice, int regularPrice, int maxPurchaseQuantity, PointType pointType, Integer pointPerPrice, ShippingFeeType shippingFeeType, Integer eachShippingFee, Boolean newArrival, Boolean best, Boolean discount, String description,  String detailInfo, String shippingInfo, String exchangeReturnInfo, ProductShowType productShowType) {

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
        this.detailInfo = detailInfo;
        this.shippingInfo = shippingInfo;
        this.exchangeReturnInfo = exchangeReturnInfo;
        this.productShowType = productShowType;
    }
}
