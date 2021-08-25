package com.hellostore.ecommerce.entity;

import com.hellostore.ecommerce.enumType.PointType;
import com.hellostore.ecommerce.enumType.ProductShowType;
import com.hellostore.ecommerce.enumType.ShippingFeeType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    private String name;

    private int price;
    private int stockQuantity;

    private PointType pointType;
    private int pointPerPrice;

    private ShippingFeeType shippingFeeType;
    private int eachShippingFee;

    private String newArrivalYn;
    private String bestYn;
    private String discountYn;

    private String description;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductOption> productOptions = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> productImages = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<CategoryProduct> categoryProducts = new ArrayList<>();

    @Lob
    private String detailInfo;

    @Lob
    private String shippingInfo;

    @Lob
    private String exchangeReturnInfo;

    @Enumerated
    private ProductShowType productShowType;
}
