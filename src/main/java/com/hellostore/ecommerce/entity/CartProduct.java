package com.hellostore.ecommerce.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    private String firstOptionName;
    private String firstOptionValue;

    private String secondOptionName;
    private String secondOptionValue;

    private int quantity;

    @Builder
    public CartProduct(Product product, Cart cart, int quantity, String firstOptionName, String firstOptionValue, String secondOptionName, String secondOptionValue) {
        this.product = product;
        this.cart = cart;
        this.quantity = quantity;
        this.firstOptionName = firstOptionName;
        this.firstOptionValue = firstOptionValue;
        this.secondOptionName = secondOptionName;
        this.secondOptionValue = secondOptionValue;
    }
}
