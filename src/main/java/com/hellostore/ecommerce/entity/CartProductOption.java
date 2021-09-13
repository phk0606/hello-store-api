package com.hellostore.ecommerce.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_product_option_id")
    private Long id;

    private Integer optionGroupNumber;
    private String optionName;
    private String optionValue;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "cart_product_id")
    private CartProduct cartProduct;

    @Builder
    public CartProductOption(CartProduct cartProduct, Integer optionGroupNumber, String optionName, String optionValue) {
        this.cartProduct = cartProduct;
        this.optionGroupNumber = optionGroupNumber;
        this.optionName = optionName;
        this.optionValue = optionValue;
    }
}
