package com.hellostore.ecommerce.entity;

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
    private Integer id;

    private String name;

    private int price;
    private int stockQuantity;

    private String pointType;
    private int pointPerPrice;

    private String shippingCostType;
    private int eachShippingCost;

    private String newArrivalYn;
    private String bestYn;
    private String discountYn;

    private String description;

    @OneToMany
    @JoinColumn(name = "image_id")
    private List<ProductImage> productImages = new ArrayList<>();
}
