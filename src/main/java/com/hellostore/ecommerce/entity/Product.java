package com.hellostore.ecommerce.entity;

import com.hellostore.ecommerce.enumType.PointType;
import com.hellostore.ecommerce.enumType.ProductShowType;
import com.hellostore.ecommerce.enumType.ShippingFeeType;
import com.hellostore.ecommerce.exception.NotEnoughStockException;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String name;

    private int salePrice;
    private int regularPrice;

//    private int stockQuantity;

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
    private int clickCount;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOption> productOptions = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> productImages = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductComment> productComments = new ArrayList<>();
    
    @Lob
    private String detailInfo;

    @Lob
    private String shippingInfo;

    @Lob
    private String exchangeReturnInfo;

    @Enumerated(EnumType.STRING)
    private ProductShowType productShowType;

//    public void addStock(int quantity) {
//        this.stockQuantity += quantity;
//    }
//
//    public void removeStock(int quantity) {
//        int restStock = this.stockQuantity - quantity;
//        if (restStock < 0) {
//            throw new NotEnoughStockException("재고 부족");
//        }
//        this.stockQuantity = restStock;
//    }

    public Product(Long id) {
        this.id = id;
    }

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

    @Builder
    public Product(Long id, String name, int salePrice, int regularPrice, int maxPurchaseQuantity, PointType pointType, Integer pointPerPrice, ShippingFeeType shippingFeeType, Integer eachShippingFee, Boolean newArrival, Boolean best, Boolean discount, String description,  String detailInfo, String shippingInfo, String exchangeReturnInfo, ProductShowType productShowType) {
        this.id = id;
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
